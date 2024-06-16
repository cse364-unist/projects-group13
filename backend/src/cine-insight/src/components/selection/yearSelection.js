export default function YearSelector({value, setValue, minYear, maxYear, ...rest}) {

    const handleYearChange = (event) => {
        setValue(event.target.value);
    };

    return (
        <select
            className="border p-2 w-full"
            value={value}
            onChange={handleYearChange}
            {...rest}
        >
            {Array.from(
                { length: maxYear - minYear + 1 },
                (_, i) => (
                    <option key={i} value={maxYear - i}>
                        {maxYear - i}
                    </option>
                )
            )}
        </select>
    );
}