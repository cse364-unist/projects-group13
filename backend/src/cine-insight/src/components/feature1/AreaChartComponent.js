import React from 'react';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const ageRanges = {
    1: "Under 18",
    18: "18-24",
    25: "25-34",
    35: "35-44",
    45: "45-49",
    50: "50-55",
    56: "56+"
};

const AreaChartComponent = ({ data, title, color }) => {
    const renderCustomTooltip = ({ active, payload }) => {
        if (active && payload && payload.length) {
            const { name, value } = payload[0].payload;
            const ageRange = ageRanges[name] || name;
            return (
                <div className="custom-tooltip" style={{ backgroundColor: '#ffffff', padding: '10px', border: '1px solid #ccc' }}>
                    <p>{`Age Range: ${ageRange}`}</p>
                    <p>{`Count: ${value}`}</p>
                </div>
            );
        }
        return null;
    };

    return (
        <div>
            <h3>{title}</h3>
            <ResponsiveContainer width="100%" height={300}>
                <AreaChart data={data}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="name" />
                    <YAxis />
                    <Tooltip content={renderCustomTooltip} />
                    <Area type="monotone" dataKey="value" stroke={color} fill={color} />
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
};

export default AreaChartComponent;
