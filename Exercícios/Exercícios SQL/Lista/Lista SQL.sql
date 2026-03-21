-- 1A) Pesquise os itens que foram vendidos sem desconto. As colunas presentes no resultado da consulta são: ID_NF, ID_ITEM, COD_PROD E VALOR_UNIT.
SELECT ID_NF, ID_ITEM, COD_PROD, VALOR_UNIT
FROM VENDAS
WHERE DESCONTO IS NULL;

-- 1B) Pesquise os itens que foram vendidos com desconto. As colunas presentes no resultado da consulta são: ID_NF, ID_ITEM, COD_PROD, VALOR_UNIT E O VALOR VENDIDO. OBS: O valor vendido é igual ao VALOR_UNIT - (VALOR_UNIT*(DESCONTO/100)).
SELECT ID_NF, ID_ITEM, COD_PROD, VALOR_UNIT,
(VALOR_UNIT * (DESCONTO / 100)) AS VALOR_VENDIDO
FROM VENDAS
WHERE DESCONTO IS NOT NULL;

-- 1C) Altere o valor do desconto (para zero) de todos os registros onde este campo é nulo.
UPDATE VENDAS
SET DESCONTO = 0
WHERE DESCONTO IS NULL;

-- 1D) Pesquise os itens que foram vendidos. As colunas presentes no resultado da consulta são: ID_NF, ID_ITEM, COD_PROD, VALOR_UNIT, VALOR_TOTAL, DESCONTO,VALOR_VENDIDO. OBS: O VALOR_TOTAL é obtido pela fórmula: QUANTIDADE * VALOR_UNIT. O VALOR_VENDIDO é igual a VALOR_UNIT - (VALOR_UNIT*(DESCONTO/100)).
SELECT ID_NF, ID_ITEM, COD_PROD, VALOR_UNIT, 
(QUANTIDADE * VALOR_UNIT) AS VALOR_TOTAL, 
DESCONTO, 
(VALOR_UNIT - (VALOR_UNIT * (COALESCE(DESCONTO, 0) / 100))) AS VALOR_VENDIDO 
FROM VENDAS;

-- 1E) Pesquise o valor total das NF e ordene o resultado do maior valor para o menor. As colunas presentes no resultado da consulta são: ID_NF, VALOR_TOTAL. OBS: O VALOR_TOTAL é obtido pela fórmula: ∑ QUANTIDADE * VALOR_UNIT. Agrupe o resultado da consulta por ID_NF.
SELECT ID_NF, 
SUM(QUANTIDADE * VALOR_UNIT) AS VALOR_TOTAL
FROM VENDAS
GROUP BY ID_NF
ORDER BY VALOR_TOTAL DESC;

-- 1F) Pesquise o valor vendido das NF e ordene o resultado do maior valor para o menor. As colunas presentes no resultado da consulta são: ID_NF, VALOR_VENDIDO. OBS: O VALOR_TOTAL é obtido pela fórmula: ∑ QUANTIDADE * VALOR_UNIT. O VALOR_VENDIDO é igual a ∑ VALOR_UNIT - (VALOR_UNIT*(DESCONTO/100)). Agrupe o resultado da consulta por ID_NF.
SELECT ID_NF,
SUM(VALOR_UNIT - (VALOR_UNIT * (COALESCE(DESCONTO, 0) / 100))) AS VALOR_VENDIDO 
FROM VENDAS 
GROUP BY ID_NF 
ORDER BY VALOR_VENDIDO DESC;

-- 1G) Consulte o produto que mais vendeu no geral. As colunas presentes no resultado da consulta são: COD_PROD, QUANTIDADE. Agrupe o resultado da consulta por COD_PROD.
SELECT COD_PROD,
SUM(QUANTIDADE) AS TOTAL_QUANTIDADE 
FROM VENDAS 
GROUP BY COD_PROD 
ORDER BY TOTAL_QUANTIDADE DESC 
LIMIT 1;

-- 1H) Consulte as NF que foram vendidas mais de 10 unidades de pelo menos um produto.As colunas presentes no resultado da consulta são: ID_NF, COD_PROD, QUANTIDADE. Agrupe o resultado da consulta por ID_NF, COD_PROD.
SELECT ID_NF, COD_PROD,
SUM(QUANTIDADE) AS TOTAL_QTDE 
FROM VENDAS 
GROUP BY ID_NF, COD_PROD 
HAVING SUM(QUANTIDADE) > 10;

-- 1I) Pesquise o valor total das NF, onde esse valor seja maior que 500, e ordene o resultado do maior valor para o menor. As colunas presentes no resultado da consulta são: ID_NF, VALOR_TOT. OBS: O VALOR_TOTAL é obtido pela fórmula: ∑ QUANTIDADE * VALOR_UNIT. Agrupe o resultado da consulta por ID_NF.
SELECT ID_NF,
SUM(QUANTIDADE * VALOR_UNIT) AS VALOR_TOT 
FROM VENDAS 
GROUP BY ID_NF 
HAVING SUM(QUANTIDADE * VALOR_UNIT) > 500 
ORDER BY VALOR_TOT DESC;

-- 1J) Qual o valor médio dos descontos dados por produto. As colunas presentes no resultado da consulta são: COD_PROD, MEDIA. Agrupe o resultado da consulta por COD_PROD.
SELECT COD_PROD,
AVG(DESCONTO) AS MEDIA
FROM VENDAS
GROUP BY COD_PROD;

-- 1K) Qual o menor, maior e o valor médio dos descontos dados por produto. As colunas presentes no resultado da consulta são: COD_PROD, MENOR, MAIOR, MEDIA. Agrupe o resultado da consulta por COD_PROD.
SELECT COD_PROD,
MIN(DESCONTO) AS MENOR,
MAX(DESCONTO) AS MAIOR,
AVG(DESCONTO) AS MEDIA
FROM VENDAS
GROUP BY COD_PROD;

-- 1L) Quais as NF que possuem mais de 3 itens vendidos. As colunas presentes no resultado da consulta são: ID_NF, QTD_ITENS. OBS:: NÃO ESTÁ RELACIONADO A QUANTIDADE VENDIDA DO ITEM E SIM A QUANTIDADE DE ITENS POR NOTA FISCAL. Agrupe o resultado da consulta por ID_NF.
SELECT ID_NF,
COUNT(ID_ITEM) AS QTD_ITENS
FROM VENDAS
GROUP BY ID_NF
HAVING QTD_ITENS > 3;

-- 2A) Encontre a MAT dos alunos com nota em BD em 2015 menor que 5 (obs: BD = código da disciplinas).
SELECT MAT
FROM Historico
WHERE COD_DISC = 'BD'
AND ANO = 2015
AND nota < 5;

-- 2B) Encontre a MAT e calcule a média das notas dos alunos na disciplina de POO em 2015.
SELECT MAT,
AVG(nota) AS MEDIA
FROM Histórico
WHERE COD_DISC = 'POO'
AND ANO = 2015
GROUP BY MAT;

-- 2C) Encontre a MAT e calcule a média das notas dos alunos na disciplina de POO em 2015 e que esta média seja superior a 6.
SELECT MAT,
AVG(nota) AS MEDIA
FROM Histórico
WHERE COD_DISC = 'POO'
AND ANO = 2015
GROUP BY MAT
HAVING MEDIA > 6;

-- 2D) Encontre quantos alunos não são de Natal.
SELECT COUNT(*) 
FROM Alunos
WHERE cidade != 'NATAL';