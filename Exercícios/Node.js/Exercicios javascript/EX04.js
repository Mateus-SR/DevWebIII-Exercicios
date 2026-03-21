// Conversão de Tipos:
// Crie uma variável numérica e uma string. Converta o número para string e a string para número. Exiba os novos tipos usando typeof.

let num = 50;
let str = "100";

let numParaStr = String(num);
let strParaNum = parseInt(str);

console.log(typeof numParaStr); // string
console.log(typeof strParaNum); // number