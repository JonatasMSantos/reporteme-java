# chmod +x clean-docker.sh
# ./clean-docker.sh

# Parar e remover todos os contêineres
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)

# Remover todas as imagens
docker rmi $(docker images -q)

# Remover todos os volumes
docker volume rm $(docker volume ls -q)

# Alternativamente, usar prune para limpar todos os volumes não utilizados
docker system prune -a --volumes -f