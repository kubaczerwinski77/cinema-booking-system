FROM node:18.12
 
WORKDIR /opt/app/frontend
 
COPY package*.json ./
 
RUN npm install
 
COPY . .
 
CMD [ "npm", "run", "dev" ]