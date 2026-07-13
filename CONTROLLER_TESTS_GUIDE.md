## Controller Integration Tests - Testing Guide

This document describes the integration tests for OpenAPI-generated REST controllers in the Ucronia application.

### Test Files Overview

The following controller integration tests have been created following the IT prefix naming convention:

| Test Class | Module | Location |
|------------|--------|----------|
| `ITTransactionController` | Transaction Adapter | `association/transactions/rest-adapter/src/test/java/...` |
| `ITAuthorController` | Library Author Adapter | `association/library/rest-adapter/src/test/java/...` |
| `ITBookTypeController` | Library BookType Adapter | `association/library/rest-adapter/src/test/java/...` |
| `ITMemberController` | Member Adapter | `association/profiles/member-rest-adapter/src/test/java/...` |

### Test Infrastructure

All tests use the `@MvcIntegrationTest` annotation which provides:
- Spring Boot test context with mocked security (@AllAuthoritiesMockUser)
- MockMvc for simulating HTTP requests
- Transaction management with rollback
- Test profile configuration (test)
- Jackson ObjectMapper for JSON serialization/deserialization

### Key Validation Constraints Tested

#### 1. Required Field Validation (`@NotNull`)

**Purpose**: Ensures that mandatory fields cannot be null.

**Example - TransactionCreationDto**:
```java
@NotNull
private Instant date;

@NotNull  
private Float amount;

@NotNull @Size(max = 200)
private String description;
```

**Tests**:
- `testCreateTransactionWithoutDate()` - Validates missing required date
- `testCreateTransactionWithoutAmount()` - Validates missing required amount
- `testCreateTransactionWithoutDescription()` - Validates missing required description

**Expected Behavior**: Requests with missing required fields return HTTP 400 (Bad Request) with error details in response body.

#### 2. Size Constraints (`@Size`)

**Purpose**: Validates minimum and/or maximum lengths for string fields.

**Example - AuthorCreationDto**:
```java
@NotNull @Size(max = 100)
private String name;
```

**Example - TransactionCreationDto**:
```java
@NotNull @Size(max = 200)
private String description;
```

**Example - BookTypeCreationDto**:
```java
@NotNull @Size(max = 100)
private String name;
```

**Tests**:
- `testCreateAuthorWithOversizedName()` - Tests 101 characters (exceeds max 100)
- `testCreateTransactionWithOversizedDescription()` - Tests 201 characters (exceeds max 200)
- `testCreateBookTypeWithOversizedName()` - Tests 101 characters (exceeds max 100)

**Expected Behavior**: Requests with field values exceeding max size return HTTP 400 with validation error.

#### 3. Numeric Constraints (`@Min`)

**Purpose**: Validates minimum values for numeric fields, particularly pagination parameters.

**Examples in Controllers**:
```java
@Min(1) @Valid final Integer page
@Min(1) @Valid final Integer size

// In AuthorController, BookTypeController:
@Min(0) @Valid final Integer page  // Page can be 0 or greater
@Min(1) @Valid final Integer size  // Size must be at least 1
```

**Tests**:
- `testGetAllTransactionsWithInvalidPageZero()` - Tests page=0 (expects failure)
- `testGetAllTransactionsWithInvalidSizeZero()` - Tests size=0 (expects failure)
- `testGetAllAuthorsWithPageZero()` - Tests page=0 (expects success for @Min(0))

**Expected Behavior**: Parameters below minimum value return HTTP 400.

#### 4. Pattern Constraints (`@Pattern`)

**Purpose**: Validates that field values match a specific regex pattern. Used primarily for sorting parameters.

**Example - MemberController**:
```java
@Valid final List<@Pattern(regexp = "^(firstName|lastName|number)\\|(asc|desc)$") String> sort
```

This pattern enforces:
- Valid field names: `firstName`, `lastName`, or `number`
- Required pipe separator: `|`
- Valid directions: `asc` or `desc`
- Example valid values: `firstName|asc`, `lastName|desc`, `number|asc`

**Tests**:
- `testGetAllMembersWithValidSort()` - Tests `firstName|asc` (valid)
- `testGetAllMembersWithInvalidSortPattern()` - Tests `firstName` without direction (invalid)
- `testGetAllMembersWithInvalidSortDirection()` - Tests `firstName|invalid` (invalid direction)

**Expected Behavior**: Sort parameters not matching the pattern return HTTP 400.

#### 5. Enum Value Validation (`@Valid` with enum DTOs)

**Purpose**: Validates that enum fields contain valid enumeration values.

**Example - MemberController**:
```java
@Valid final MemberStatusDto status
```

Where `MemberStatusDto` is an enum with valid values like `ACTIVE`, `INACTIVE`.

**Tests**:
- `testGetAllMembersWithActiveStatus()` - Tests valid enum value
- `testGetAllMembersWithInactiveStatus()` - Tests alternative valid enum value

**Expected Behavior**: Only valid enum values are accepted; invalid values return HTTP 400.

#### 6. Nested Object Validation

**Purpose**: Recursively validates constraints on nested objects within request bodies.

**Example - MemberCreationDto**:
```java
@Valid
private NameDto name;  // Nested object

// Where NameDto contains:
@NotNull
private String firstName;

@NotNull  
private String lastName;
```

**Tests**:
- `testCreateMemberWithoutName()` - Missing entire name object
- `testCreateMemberWithIncompleteName()` - Missing lastName in nested object

**Expected Behavior**: Violations at any nesting level return HTTP 400.

### Request/Response Body Mapping Tests

#### JSON Deserialization

**Tests validate proper mapping of JSON request bodies to DTOs**:

1. **Basic Field Mapping**: Verify JSON fields map to DTO properties correctly
   ```java
   mockMvc.perform(post("/transactions")
       .contentType(MediaType.APPLICATION_JSON)
       .content("""{"date": "2025-08-01T00:00:00Z", ...}"""))
   ```

2. **Temporal Type Handling**: ISO 8601 date/time deserialization
   ```java
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
   private Instant date;
   ```

3. **Quoted String Fields**: Proper handling of JSON string escaping
   ```java
   private String description;  // Always quoted in JSON
   ```

4. **Optional Fields**: Fields that may be null or absent in JSON
   ```java
   @Valid final Instant from  // Optional parameter
   @Nullable SettingDto content;
   ```

#### JSON Serialization

**Tests validate response DTOs are properly serialized to JSON**:

1. **Response Field Presence**: Verify expected fields exist in response
   ```java
   .andExpect(jsonPath("$.name").exists())
   .andExpect(jsonPath("$.number").exists())
   ```

2. **Field Value Correctness**: Verify values match expected
   ```java
   .andExpect(jsonPath("$.name", equalTo("Test Value")))
   ```

3. **Content-Type**: Verify responses set correct Content-Type
   ```java
   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
   ```

### Pagination and Sorting Tests

#### Pagination Parameters

**Tests validate pagination parameter handling**:

1. **Page Parameter**:
   - Minimum value: varies by controller (@Min(0) or @Min(1))
   - Type: Integer
   - Used for: offsets into result set

2. **Size Parameter**:
   - Minimum value: @Min(1) (across all controllers)
   - Type: Integer  
   - Used for: number of items per page

3. **Invalid Pagination**:
   - Boundary testing: page=0, page=-1, size=0, size=-1
   - Expected: HTTP 400 for values below minimum

#### Sorting Parameters

**Tests validate sort parameter parsing and direction handling**:

1. **Sort Format**: `field|direction` (controller-specific field names)
   - TransactionController: supports standard list sorting
   - MemberController: constrained to `firstName|asc`, `lastName|desc`, `number|asc`

2. **Sort Direction Values**:
   - Valid: `asc` (ascending), `desc` (descending)
   - Invalid values: Any other string value

3. **Multiple Sort Fields**:
   ```java
   .param("sort", "field1:asc,field2:desc")
   ```

### OpenAPI Generated Code Handling

#### Generated DTO Characteristics

All DTOs are generated by OpenAPI Generator with:

1. **Constructor Patterns**:
   - Default no-arg constructor
   - Constructor with required-only parameters
   - Builder-style setters returning `this` for chaining

2. **Validation Annotations**:
   - Applied directly to generated getters
   - Includes: `@NotNull`, `@Size`, `@Pattern`, `@Valid`

3. **JSON Property Mapping**:
   ```java
   @JsonProperty("fieldName")
   public String getFieldName() { ... }
   ```

4. **Special Annotation Handling**:
   - `@Nullable`: Field can be null
   - `@Valid`: Recursive validation of nested objects
   - `@JsonNullable`: Jackson's nullable wrapper for optional fields

#### Test Coverage for Generated Code

**Tests ensure generated code is properly validated**:

1. **Constraint Application**: Verify `@NotNull`, `@Size`, etc. are enforced
2. **Constructor Validation**: Test both no-arg and required-parameter constructors
3. **Builder Pattern**: Test setter chaining and getter retrieval
4. **Equality**: Verify `equals()` and `hashCode()` work correctly
5. **String Representation**: Verify `toString()` output format

### Special Cases and Edge Cases

#### Whitespace Handling

Tests validate handling of whitespace in string fields:
```java
// Padded name test
.param("name", "  John  ")
```

**Expected**: Framework may trim whitespace based on configuration.

#### Special Characters

Tests validate JSON escaping for special characters:
```java
// Request with special characters in name
{"name": "J.R.R. Tolkien"}
{"name": "Fantasy & Sci-Fi (Extended)"}
```

**Expected**: Characters are properly escaped/unescaped in JSON.

#### Empty Strings

Tests validate handling of empty string values:
```java
// Empty name
{"name": ""}
```

**Expected**: Depends on constraints; @NotBlank rejects, @NotNull may accept.

#### Unknown Fields in JSON

Tests validate behavior when JSON contains unexpected properties:
```java
{
    "name": "Test",
    "unknownField": "value"
}
```

**Expected**: Typically ignored by Jackson (configured via MapperFeature.FAIL_ON_UNKNOWN_PROPERTIES).

### Running the Tests

#### Run All Controller Tests
```bash
mvn test -Dtest=IT*Controller
```

#### Run Specific Test Class
```bash
mvn test -Dtest=ITTransactionController
```

#### Run Specific Test Method
```bash
mvn test -Dtest=ITTransactionController#testCreateTransactionWithValidData
```

#### Run with Coverage Report
```bash
mvn clean test jacoco:report
```

### Test Organization Best Practices

1. **Naming Convention**: `IT<ComponentName>` for integration test classes
2. **Method Naming**: Descriptive names with pattern `test<Operation><Condition>`
3. **DisplayNames**: Human-readable via `@DisplayName` annotation
4. **Grouping**: Tests organized by operation (Create, List, Get, Update, Delete)
5. **Documentation**: Each test method has JavaDoc explaining what's validated

### Assertion Patterns

Common hamcrest matchers used:

| Matcher | Purpose | Example |
|---------|---------|---------|
| `equalTo()` | String/value equality | `.andExpect(jsonPath("$.name", equalTo("Test")))` |
| `hasItem()` | Collection contains item | `.andExpect(jsonPath("$.errors", hasItem(...)))` |
| `containsString()` | String substring match | `hasItem(containsString("field"))` |
| `exists()` | Field present in response | `.andExpect(jsonPath("$.id").exists())` |
| `isArray()` | Field is array | `.andExpect(jsonPath("$.content").isArray())` |
| `isA()` | Type check | `.andExpect(jsonPath("$", isA(Map.class)))` |

### HTTP Status Codes

Tests validate appropriate status codes:

| Status | Meaning | Test Examples |
|--------|---------|---|
| 200 OK | Successful operation | All valid requests |
| 400 Bad Request | Validation failure | Missing required fields, constraint violations |
| 404 Not Found | Resource not found | GET non-existent ID |
| 500 Server Error | Unexpected error | Service layer exceptions |

### Future Extensions

Potential areas for additional testing:

1. **Security**: Authorization checks with different user roles
2. **Concurrent Requests**: Thread safety and race condition testing
3. **Large Payloads**: Tests with maximum size request bodies
4. **Performance**: Response time validation for pagination
5. **Integration**: End-to-end tests combining multiple operations
6. **Error Scenarios**: Service layer exception handling
7. **Custom Validators**: Application-specific validation rules
