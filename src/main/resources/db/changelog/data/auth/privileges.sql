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

INSERT INTO privileges (id, name) VALUES
   (1, 'FEE:CREATE'),
   (2, 'FEE:READ'),
   (3, 'FEE:UPDATE'),
   (4, 'FEE:DELETE'),
   (5, 'MEMBER:CREATE'),
   (6, 'MEMBER:READ'),
   (7, 'MEMBER:UPDATE'),
   (8, 'MEMBER:DELETE'),
   (9, 'TRANSACTION:CREATE'),
   (10, 'TRANSACTION:READ'),
   (11, 'TRANSACTION:UPDATE'),
   (12, 'TRANSACTION:DELETE'),
   (13, 'BALANCE:READ'),
   (14, 'FEE_YEAR:READ'),
   (15, 'MEMBER_STATS:READ');

INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN');

INSERT INTO role_privileges (role_id, privilege_id) VALUES
   (1, 1),
   (1, 2),
   (1, 3),
   (1, 4),
   (1, 5),
   (1, 6),
   (1, 7),
   (1, 8),
   (1, 9),
   (1, 10),
   (1, 11),
   (1, 12),
   (1, 13),
   (1, 14),
   (1, 15);
