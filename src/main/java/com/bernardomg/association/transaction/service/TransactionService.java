
package com.bernardomg.association.transaction.service;

import java.util.Optional;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;

/**
 * Transaction service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface TransactionService {

    /**
     * Persists the received transaction.
     *
     * @param transaction
     *            transaction to persist
     * @return the persisted transaction
     */
    public Transaction create(final Transaction transaction);

    /**
     * Deletes the transaction with the received id.
     *
     * @param id
     *            id of the transaction to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the transactions matching the sample. If the sample fields are empty, then all the transactions are
     * returned.
     *
     * @param sample
     *            sample for filtering
     * @param pagination
     *            pagination to apply
     * @param sort
     *            sorting to apply
     * @return all the transactions matching the sample
     */
    public Iterable<Transaction> getAll(final Transaction sample, final Pagination pagination, final Sort sort);

    /**
     * Returns the transaction for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the transaction to acquire
     * @return an {@code Optional} with the transaction, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Transaction> getOne(final Long id);

    /**
     * Updates the transaction for the received id with the received data.
     *
     * @param id
     *            id of the transaction to update
     * @param transaction
     *            new data for the transaction
     * @return the updated transaction
     */
    public Transaction update(final Long id, final Transaction transaction);

}
