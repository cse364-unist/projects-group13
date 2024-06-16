import React from 'react';
import { PieChart, Pie, Tooltip, Cell } from 'recharts';

const PieChartComponent = ({ data, title, color }) => {
    const renderCustomTooltip = ({ active, payload }) => {
        if (active && payload && payload.length) {
            const { name, value } = payload[0];
            const total = data.reduce((acc, entry) => acc + entry.value, 0);
            const percent = ((value / total) * 100).toFixed(2);
            const gender = name === 'M' ? 'Male' : 'Female';
            return (
                <div className="custom-tooltip" style={{ backgroundColor: '#ffffff', padding: '10px', border: '1px solid #ccc' }}>
                    <p>{`Gender: ${gender}`}</p>
                    <p>{`Count: ${value}`}</p>
                    <p>{`Rate: ${percent}%`}</p>
                </div>
            );
        }
        return null;
    };

    return (
        <div>
            <h3>{title}</h3>
            <PieChart width={300} height={300}>
                <Pie
                    data={data}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={70}
                    fill={color}
                    label
                >
                    {data.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={color} />
                    ))}
                </Pie>
                <Tooltip content={renderCustomTooltip} />
            </PieChart>
        </div>
    );
};

export default PieChartComponent;
