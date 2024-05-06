const express = require(‘express’);
const mysql = require(‘mysql’);
const app = express();
const port = 3000; // we can change our port

const db = mysql.createConnection({
host: ‘localhost’,
user: ‘root’,
password: ‘ ’,
database: ‘your_database_name’,
});

db.connect((err) => {
if (err) throw err;
console.log(‘Connected to MySQL database’);
});

app.use(express.json());