--
--  The MIT License (MIT)
--
--  Copyright (c) 2017 Bernardo Martínez Garrido
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
-- This SQL script populates the initial sequences.
-- ****************************************

INSERT INTO actions (id, name) VALUES
   (1, 'CREATE'),
   (2, 'READ'),
   (3, 'UPDATE'),
   (4, 'DELETE');

INSERT INTO resources (id, name) VALUES
   (1, 'MEMBER'),
   (2, 'FEE'),
   (3, 'TRANSACTION'),
   (4, 'BALANCE'),
   (5, 'USER'),
   (6, 'ROLE'),
   (7, 'ACTION'),
   (8, 'RESOURCE');

INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN'),
   (2, 'READ');

INSERT INTO role_permissions (role_id, resource_id, action_id, granted) VALUES
   ----------------
   -- ADMIN role --
   ----------------
   -- MEMBER
   (1, 1, 1, true),
   (1, 1, 2, true),
   (1, 1, 3, true),
   (1, 1, 4, true),
   -- FEE
   (1, 2, 1, true),
   (1, 2, 2, true),
   (1, 2, 3, true),
   (1, 2, 4, true),
   -- TRANSACTION
   (1, 3, 1, true),
   (1, 3, 2, true),
   (1, 3, 3, true),
   (1, 3, 4, true),
   -- BALANCE
   (1, 4, 1, true),
   (1, 4, 2, true),
   (1, 4, 3, true),
   (1, 4, 4, true),
   -- USER
   (1, 5, 1, true),
   (1, 5, 2, true),
   (1, 5, 3, true),
   (1, 5, 4, true),
   -- ROLE
   (1, 6, 1, true),
   (1, 6, 2, true),
   (1, 6, 3, true),
   (1, 6, 4, true),
   -- ACTION
   (1, 7, 1, true),
   (1, 7, 2, true),
   (1, 7, 3, true),
   (1, 7, 4, true),
   -- RESOURCE
   (1, 8, 1, true),
   (1, 8, 2, true),
   (1, 8, 3, true),
   (1, 8, 4, true),
   ----------------
   -- READ role --
   ----------------
   -- MEMBER
   (2, 1, 1, false),
   (2, 1, 2, true),
   (2, 1, 3, false),
   (2, 1, 4, false),
   -- FEE
   (2, 2, 1, false),
   (2, 2, 2, true),
   (2, 2, 3, false),
   (2, 2, 4, false),
   -- TRANSACTION
   (2, 3, 1, false),
   (2, 3, 2, true),
   (2, 3, 3, false),
   (2, 3, 4, false),
   -- BALANCE
   (2, 4, 1, false),
   (2, 4, 2, true),
   (2, 4, 3, false),
   (2, 4, 4, false);
