INSERT INTO dbo.category (category_id, parent_category_id, internal_id, description)
VALUES
(1010, null, 20, 'Subject_Order'),
(1011, 1010, 21, 'SO_Credits'),
(1012, 1010, 22, 'SO_Credits_Rev'),
(1013, 1010, 23, 'SO_Required'),
(1014, 1010, 24, 'SO_Required_Rev'),
(1015, 1010, 25, 'SO_Index'),
(1016, 1010, 26, 'SO_Index_Rev'),
(1017, 1010, 27, 'SO_Periods'),
(1018, 1010, 28, 'SO_Periods_Rev'),
(1019, 1010, 29, 'SO_Assigned_Students'),
(1020, 1010, 30, 'SO_Assigned_Students_Rev'),
(1021, 1010, 31, 'SO_Shuffle');
