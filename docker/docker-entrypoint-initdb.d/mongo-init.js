db.createUser(
    {
        user: "root",
        pwd: "toor",
        roles: [
            {
                role: "readWrite",
                db: "sample_db"
            }
        ]
    }
);

db.createCollection('clientes');
db.clientes.insertMany([
    {
        "cpf": "98903745019",
        "nome": "Rafael",
        "sobrenome": "Jardim",
        "endereco": {
            "rua": "Rua teste",
            "bairro": "Bairro Teste",
            "quadra": "Quadra",
            "lote": "1",
            "numero": "1",
            "cidade": "Brasília",
            "complemento": "",
            "estado": "DF",
            "cep": "71907000"
        },
        "email": "rafaelfgjardim@gmail.com",
        "telefone": "62999655551",
        "comunicacoes": {
            "email": true,
            "sms": false,
            "whatsapp": false,
            "telegram": false
        },
        "_class": "com.example.wishlistsbbe.entity.Cliente"
    },
    {
        "cpf": "92357502029",
        "nome": "Rafael",
        "sobrenome": "2",
        "endereco": {
            "rua": "Rua teste",
            "bairro": "Bairro Teste",
            "quadra": "Quadra",
            "lote": "1",
            "numero": "1",
            "cidade": "Brasília",
            "complemento": "",
            "estado": "DF",
            "cep": "71907000"
        },
        "email": "rafaelfgjardim@gmail.com",
        "telefone": "62999655551",
        "comunicacoes": {
            "email": true,
            "sms": false,
            "whatsapp": false,
            "telegram": false
        },
        "_class": "com.example.wishlistsbbe.entity.Cliente"
    }
]);

db.createCollection('wishlist');
db.wishlist.insertMany([
    {
        "documento": "92357502029",
        "produtosList": [
            {
                "$ref": "produtos",
                "$id": {
                    "$oid": "64bdc59f3064e749bc8d28b0"
                }
            }
        ],
        "dataCriacao": {
            "$date": "2023-07-26T12:19:31.618Z"
        },
        "dataAtualizacao": {
            "$date": "2023-07-26T12:19:31.618Z"
        },
        "_class": "com.example.wishlistsbbe.entity.Wishlist"
    }
]);

db.createCollection('produtos');
db.produtos.insertMany([
    {
        "codbarra": "11111",
        "descricao": "Processador AMD Ryzen 7",
        "fornecedor": "AMD",
        "valor": 1234.99,
        "quantidadeEstoque": 10,
        "_class": "com.example.wishlistsbbe.entity.Produtos"
    },
    {
        "codbarra": "11112",
        "descricao": "Processador AMD Ryzen 7",
        "fornecedor": "AMD",
        "valor": 1234.99,
        "quantidadeEstoque": 10,
        "_class": "com.example.wishlistsbbe.entity.Produtos"
    },
    {

        "codbarra": "11113",
        "descricao": "Processador AMD Ryzen 7",
        "fornecedor": "AMD",
        "valor": 1234.99,
        "quantidadeEstoque": 10,
        "_class": "com.example.wishlistsbbe.entity.Produtos"
    },
    {
        "codbarra": "11114",
        "descricao": "Processador AMD Ryzen 7",
        "fornecedor": "AMD",
        "valor": 1234.99,
        "quantidadeEstoque": 10,
        "_class": "com.example.wishlistsbbe.entity.Produtos"
    },
    {
        "codbarra": "11115",
        "descricao": "Processador AMD Ryzen 7",
        "fornecedor": "AMD",
        "valor": 1234.99,
        "quantidadeEstoque": 10,
        "_class": "com.example.wishlistsbbe.entity.Produtos"
    },
    {
        "codbarra": "11116",
        "descricao": "Processador AMD Ryzen 7",
        "fornecedor": "AMD",
        "valor": 1234.99,
        "quantidadeEstoque": 10,
        "_class": "com.example.wishlistsbbe.entity.Produtos"
    }
]);