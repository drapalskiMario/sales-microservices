FROM node:14
WORKDIR /usr/app

COPY package.json /usr/app

RUN npm i

COPY . /usr/app

EXPOSE 3002

CMD ["npm", "run", "build"]