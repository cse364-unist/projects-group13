import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
/**
 * data example
 * const data = [
  { name: "Option A", person: 10 },
  { name: "Option B", person: 52 },
  { name: "Option C", person: 42 },
  { name: "Option D", person: 23 },
  { name: "Option E", person: 12 },
  { name: "Option F", person: 46 },
  { name: "Option G", person: 34 },
  ];
 */

const BarGraph = ({ data }) => {
  return (
    <ResponsiveContainer width="100%" height="100%">
      <BarChart
        width={500}
        height={300}
        data={data}
        margin={{
          top: 5,
          right: 30,
          left: 20,
          bottom: 5,
        }}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar dataKey="person" fill="#8884d8" />
      </BarChart>
    </ResponsiveContainer>
  );
};

export default BarGraph;
