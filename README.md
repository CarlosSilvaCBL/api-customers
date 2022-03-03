# Serviço de Clientes

### Spring application

### Tecnologias
- Spring-boot
- PostgreSQL
- Stubby4j
- Lombok
- Swagger
- Docke-Compose

### Pré-requisitos 
  - Versão 11 do java
  
    ```

## Para rodar o docker-compose

Para isso navegue até a raiz do projeto e execute o comando abaixo:
```bash
docker-compose up
```

Em alguns casos, após executar o comando stop as políticas de restart não é 
atualizada e então se faz necessário a execução do comando abaixo, 
então se comando a cima não funcionar, esta pode ser uma opção:
```bash
docker-compose -f docker-compose.yml --compatability up
```

## Para parar o docker-compose

Execute o comando abaixo:
```bash
docker-compose stop
```
