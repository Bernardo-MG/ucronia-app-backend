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
   (1, 'DEFAULT'),
   (2, 'CREATE_FEE'),
   (3, 'READ_FEE'),
   (4, 'UPDATE_FEE'),
   (5, 'DELETE_FEE'),
   (6, 'CREATE_MEMBER'),
   (7, 'READ_MEMBER'),
   (8, 'UPDATE_MEMBER'),
   (9, 'DELETE_MEMBER'),
   (10, 'CREATE_TRANSACTION'),
   (11, 'READ_TRANSACTION'),
   (12, 'UPDATE_TRANSACTION'),
   (13, 'DELETE_TRANSACTION'),
   (13, 'READ_BALANCE'),
   (13, 'READ_FEE_YEAR'),
   (13, 'READ_MEMBER_STATS');

INSERT INTO roles (id, name) VALUES
   (1, 'ADMIN');

INSERT INTO role_privileges (role_id, privilege_id) VALUES
   (1, 1);
