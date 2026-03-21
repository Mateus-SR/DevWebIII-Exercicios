// Tabuada com Input:
// Crie um programa que receba um número do usuário e exiba sua tabuada, solicitando a entrada de dados (o exercício pede para implementar entrada de usuário no terminal).

// node '.\Exercícios\Node.js\Exercicios javascript\EX06.js'

const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
  });
  
  readline.question('Digite um número para ver a tabuada: ', (num) => {
    const n = Number(num);
    console.log(`\nTabuada do ${n}:`);
    for (let i = 1; i <= 10; i++) {
      console.log(`${n} x ${i} = ${n * i}`);
    }
    
    readline.close();
  });