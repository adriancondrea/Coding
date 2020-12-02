GO
CREATE VIEW RandomView
AS
SELECT RAND() AS Value
GO


--DROP FUNCTION function_random_int
GO
CREATE FUNCTION function_random_int(
	@leftBound INT,
	@rightBound INT
)
RETURNS INT
AS
BEGIN
	RETURN FLOOR((SELECT Value FROM RandomView) * (@rightBound - @leftBound) + @leftBound)
END
GO

CREATE PROCEDURE sp_insert_Circuit @seed INT
AS
    BEGIN
        INSERT INTO Circuit(Location, Length, Type) VALUES
        (
         'Location'+convert(varchar(10), @seed),
          @seed,
          'Type' + convert(varchar(10), @seed)
        )
    end