import React, { useState } from "react";
import axios from "axios";

function App() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [transactions, setTransactions] = useState([]);
  const [dashboard, setDashboard] = useState({});
  const [tx, setTx] = useState({ type: "", category: "", amount: "", date: "" });

  const API_URL = "http://localhost:8080/api";

  const registerUser = async () => {
    if (!username || !email || !password) return alert("Fill all fields");
    try {
      await axios.post(`${API_URL}/auth/register`, { username, email, password });
      alert("User Registered Successfully");
    } catch (error) {
      console.error(error);
      alert("Error registering user");
    }
  };

  const addTransaction = async () => {
    if (!tx.type || !tx.category || !tx.amount || !tx.date) {
      return alert("Fill all transaction fields");
    }
    try {
      await axios.post(`${API_URL}/transactions/add/${username}`, {
        username,
        type: tx.type,
        category: tx.category,
        amount: parseFloat(tx.amount),
        date: tx.date,
      });
      alert("Transaction Added");
      setTx({ type: "", category: "", amount: "", date: "" }); // reset form
    } catch (error) {
      console.error(error);
      alert("Error adding transaction");
    }
 };


  const showTransactions = async () => {
    try {
      const res = await axios.get(`${API_URL}/transactions/${username}`);
      setTransactions(res.data);
    } catch (error) {
      console.error(error);
      alert("Error fetching transactions");
    }
  };

  const showDashboard = async () => {
    if (!username) return alert("Enter a username first");
    try {
      const res = await axios.get(`${API_URL}/dashboard/${username}/summary`);
      console.log("Dashboard API Response:", res.data);
      setDashboard(res.data);
    } catch (error) {
      console.error("Dashboard fetch error:", error);
    }
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial" }}>
      <h1>Finance Dashboard</h1>

      {/* User Registration */}
      <div style={{ marginBottom: "20px" }}>
        <h2>Register User</h2>
        <input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button onClick={registerUser}>Register</button>
      </div>

      {/* Add Transaction */}
      <div style={{ marginBottom: "20px" }}>
        <h2>Add Transaction</h2>
        <select
          value={tx.type}
          onChange={(e) => setTx({ ...tx, type: e.target.value })}
        >
          <option value="">Select Type</option>
          <option value="income">Income</option>
          <option value="expense">Expense</option>
        </select>
        <input
          placeholder="Category"
          value={tx.category}
          onChange={(e) => setTx({ ...tx, category: e.target.value })}
        />
        <input
          placeholder="Amount"
          type="number"
          value={tx.amount}
          onChange={(e) => setTx({ ...tx, amount: e.target.value })}
        />
        <input
          type="date"
          value={tx.date}
          onChange={(e) => setTx({ ...tx, date: e.target.value })}
        />
        <button onClick={addTransaction}>Add Transaction</button>
      </div>


      {/* Show Transactions */}
      <div style={{ marginBottom: "20px" }}>
        <h2>Transactions</h2>
        <button onClick={showTransactions}>Show Transactions</button>
        <ul>
          {transactions.map((t, index) => (
            <li key={index}>
              {t.name} - {t.amount}
            </li>
          ))}
        </ul>
      </div>

      {/* Dashboard */}
      <div>
        <h2>Dashboard</h2>
        <button onClick={showDashboard}>Show Dashboard</button>
        <div style={{ marginTop: "10px", background: "#f4f4f4", padding: "10px" }}>
          <pre>{JSON.stringify(dashboard, null, 2)}</pre>
        </div>
      </div>
    </div>
  );
}

export default App;