/**
 * Servidor Node.js com SQLite à prova de recarregamentos do Live Server
 * * Dependências: npm install express sqlite3 cors
 * Executar: node servidor.js
 */

const express = require('express');
const sqlite3 = require('sqlite3').verbose();
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();

// Middlewares
app.use(express.json());
app.use(cors());

// --- CORREÇÃO DO PROBLEMA DE RECARREGAMENTO ---
// Cria uma pasta oculta '.dados' para a base de dados.
// O Live Server ignora pastas ocultas, impedindo que a página recarregue
// quando a base de dados for atualizada!
const dbDir = path.join(__dirname, '.dados');
if (!fs.existsSync(dbDir)) {
    fs.mkdirSync(dbDir);
}

const dbPath = path.join(dbDir, 'agenda.sqlite');

const db = new sqlite3.Database(dbPath, (err) => {
    if (err) {
        console.error('Erro ao conectar à base de dados:', err.message);
    } else {
        console.log('Conectado à base de dados SQLite (pasta oculta .dados).');
        db.run(`CREATE TABLE IF NOT EXISTS eventos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titulo TEXT NOT NULL,
            data TEXT NOT NULL,
            hora TEXT NOT NULL,
            descricao TEXT,
            criado_em DATETIME DEFAULT CURRENT_TIMESTAMP
        )`);
    }
});

// --- ROTAS CRUD ---

// CREATE
app.post('/api/eventos', (req, res) => {
    const { titulo, data, hora, descricao } = req.body;
    
    if (!titulo || !data || !hora) {
        return res.status(400).json({ erro: 'Título, data e hora são obrigatórios.' });
    }

    const query = 'INSERT INTO eventos (titulo, data, hora, descricao) VALUES (?, ?, ?, ?)';
    db.run(query, [titulo, data, hora, descricao], function(erro) {
        if (erro) return res.status(500).json({ erro: erro.message });
        res.status(201).json({ id: this.lastID, titulo, data, hora, descricao });
    });
});

// READ
app.get('/api/eventos', (req, res) => {
    const query = 'SELECT * FROM eventos ORDER BY data ASC, hora ASC';
    db.all(query, [], (erro, resultados) => {
        if (erro) return res.status(500).json({ erro: erro.message });
        res.json(resultados);
    });
});

// UPDATE
app.put('/api/eventos/:id', (req, res) => {
    const { id } = req.params;
    const { titulo, data, hora, descricao } = req.body;
    
    const query = 'UPDATE eventos SET titulo = ?, data = ?, hora = ?, descricao = ? WHERE id = ?';
    db.run(query, [titulo, data, hora, descricao, id], function(erro) {
        if (erro) return res.status(500).json({ erro: erro.message });
        if (this.changes === 0) return res.status(404).json({ erro: 'Evento não encontrado.' });
        res.json({ mensagem: 'Evento atualizado com sucesso!' });
    });
});

// DELETE
app.delete('/api/eventos/:id', (req, res) => {
    const { id } = req.params;
    
    const query = 'DELETE FROM eventos WHERE id = ?';
    db.run(query, [id], function(erro) {
        if (erro) return res.status(500).json({ erro: erro.message });
        if (this.changes === 0) return res.status(404).json({ erro: 'Evento não encontrado.' });
        res.json({ mensagem: 'Evento eliminado com sucesso!' });
    });
});

// Iniciar o servidor
const PORTA = process.env.PORT || 3000;
app.listen(PORTA, () => {
    console.log(`Servidor rodando na porta ${PORTA}`);
});