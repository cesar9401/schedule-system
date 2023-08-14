INSERT INTO dbo.category (category_id, parent_category_id, internal_id, description)
VALUES
(1000, null, 10, 'Day_of_Week'),
(1001, 1000, 11, 'DW_Monday'),
(1002, 1000, 12, 'DW_Tuesday'),
(1003, 1000, 13, 'DW_Wednesday'),
(1004, 1000, 14, 'DW_Thursday'),
(1005, 1000, 15, 'DW_Friday'),
(1006, 1000, 16, 'DW_Saturday'),
(1007, 1000, 17, 'DW_Sunday');
