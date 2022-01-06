#!/bin/sh
javac -d target/CoffeeMaker_Web/WEB-INF/classes src/main/java/edu/ncsu/csc326/coffeemaker/*.java src/main/java/edu/ncsu/csc326/coffeemaker/exceptions/*.java 
cp src/main/webapp/add_inventory.jsp target/CoffeeMaker_Web
cp src/main/webapp/add_recipe.jsp target/CoffeeMaker_Web
cp src/main/webapp/check_inventory.jsp target/CoffeeMaker_Web
cp src/main/webapp/delete_recipe.jsp target/CoffeeMaker_Web
cp src/main/webapp/edit_recipe.jsp target/CoffeeMaker_Web
cp src/main/webapp/head.jsp target/CoffeeMaker_Web
cp src/main/webapp/index.jsp target/CoffeeMaker_Web
cp src/main/webapp/make_coffee.jsp target/CoffeeMaker_Web
cp -r src/main/webapp/WEB-INF/web.xml target/CoffeeMaker_Web/WEB-INF
cd target/CoffeeMaker_Web
jar -cvf ../CoffeeMaker_Web.war *