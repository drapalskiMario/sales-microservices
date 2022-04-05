FROM node:14
WORKDIR .
COPY package.json ./
RUN npm i
COPY . .
EXPOSE 3002
CMD ["npm", "run", "build"]