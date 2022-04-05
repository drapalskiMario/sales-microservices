FROM node:14
WORKDIR .
COPY package.json ./
RUN npm i
COPY . .
EXPOSE 3000
CMD ["npm", "start"]