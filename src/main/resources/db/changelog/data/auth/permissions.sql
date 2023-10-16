--
--  The MIT License (MIT)
--
--  Copyright (c) 2023 Bernardo Martínez Garrido
--  
--  Permission is hereby granted, free of charge, to any person obtaining a copy
--  of this software and associated documentation files (the "Software"), to deal
--  in the Software without restriction, including without limitation the rights
--  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
--  copies of the Software, and to permit persons to whom the Software is
--  furnished to do so, subject to the following conditions:
--  
--  The above copyright notice and this permission notice shall be included in all
--  copies or substantial portions of the Software.
--  
--  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
--  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
--  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
--  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
--  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
--  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
--  SOFTWARE.
--


-- ****************************************
-- This SQL script populates the initial permissions data.
-- ****************************************

INSERT INTO actions (id, name) VALUES
   (1, 'CREATE'),
   (2, 'READ'),
   (3, 'UPDATE'),
   (4, 'DELETE'),
   (5, 'VIEW');

INSERT INTO resources (id, name) VALUES
   (1, 'USER'),
   (2, 'ROLE'),
   -- (3, 'ACTION'),
   -- (4, 'RESOURCE'),
   (5, 'USER-TOKEN'),
   (10, 'MEMBER'),
   (11, 'FEE'),
   (12, 'TRANSACTION'),
   (13, 'BALANCE'),
   (15, 'ASSOCIATION_CONFIGURATION'),
   (16, 'FUNDS'),
   (17, 'MEMBERSHIP');

INSERT INTO permissions (id, resource, action) VALUES
   -- Security
   (1, 'USER', 'CREATE'),
   (2, 'USER', 'READ'),
   (3, 'USER', 'UPDATE'),
   (4, 'USER', 'DELETE'),
   (5, 'ROLE', 'CREATE'),
   (6, 'ROLE', 'READ'),
   (7, 'ROLE', 'UPDATE'),
   (8, 'ROLE', 'DELETE'),
   -- Security views
   (12, 'USER', 'VIEW'),
   (13, 'ROLE', 'VIEW'),
   -- User tokens
   (14, 'USER-TOKEN', 'READ'),
   (15, 'USER-TOKEN', 'VIEW'),
   -- Association data
   (50, 'MEMBER', 'CREATE'),
   (51, 'MEMBER', 'READ'),
   (52, 'MEMBER', 'UPDATE'),
   (53, 'MEMBER', 'DELETE'),
   (54, 'FEE', 'CREATE'),
   (55, 'FEE', 'READ'),
   (56, 'FEE', 'UPDATE'),
   (57, 'FEE', 'DELETE'),
   (58, 'TRANSACTION', 'CREATE'),
   (59, 'TRANSACTION', 'READ'),
   (60, 'TRANSACTION', 'UPDATE'),
   (61, 'TRANSACTION', 'DELETE'),
   (62, 'ASSOCIATION_CONFIGURATION', 'CREATE'),
   (63, 'ASSOCIATION_CONFIGURATION', 'READ'),
   (64, 'ASSOCIATION_CONFIGURATION', 'UPDATE'),
   (65, 'ASSOCIATION_CONFIGURATION', 'DELETE'),
   -- Association generated data
   (66, 'BALANCE', 'READ'),
   -- Association views
   (67, 'FUNDS', 'VIEW'),
   (68, 'MEMBERSHIP', 'VIEW'),
   (69, 'ASSOCIATION_CONFIGURATION', 'VIEW');
