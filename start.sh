docker-compose up -d &&
sleep 2 && # Wait for the openlad server to initialize
docker exec -it openldap bash -c "ldapmodify -h localhost -p 389 -w '123456' -D 'cn=admin,dc=techinterview,dc=com'  <  create_ou_users.ldif" &&
echo "Succeed!"
