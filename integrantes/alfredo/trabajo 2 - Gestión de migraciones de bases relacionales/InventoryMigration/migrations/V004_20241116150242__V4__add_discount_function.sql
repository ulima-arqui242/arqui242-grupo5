CREATE OR REPLACE FUNCTION apply_discount(price NUMERIC, discount NUMERIC)
RETURNS NUMERIC AS $$
BEGIN
    RETURN price - (price * discount / 100);
END;
$$ LANGUAGE plpgsql;