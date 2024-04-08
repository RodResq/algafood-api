insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into restaurante(nome, taxa_frete, cozinha_id) values ('Pad Thai', 10.5, 1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ('Cozinha Tom Yum', 90.5, 1);
insert into restaurante(nome, taxa_frete, cozinha_id) values ('Namaskar', 5.5, 2);

insert into forma_pagamento(descricao) values ('A vista');
insert into forma_pagamento(descricao) values ('Credito');
insert into forma_pagamento(descricao) values ('Debito');

insert into permissao(descricao) values ('Admin');
insert into permissao(descricao) values ('User');

insert into estado(nome) values ('Para');
insert into estado(nome) values ('Rio de Janeiro');

insert into cidade(nome, estado_id) values ('Belem', 1);
insert into cidade(nome, estado_id) values ('Angra dos Reis', 2);

