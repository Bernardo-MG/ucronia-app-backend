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
   (3, 'ACTION'),
   (4, 'RESOURCE'),
   (5, 'TOKEN'),
   (10, 'MEMBER'),
   (11, 'FEE'),
   (12, 'TRANSACTION'),
   (13, 'BALANCE'),
   (14, 'STATS'),
   (15, 'ASSOCIATION_CONFIGURATION'),
   (16, 'FUNDS'),
   (16, 'MEMBERSHIP');
