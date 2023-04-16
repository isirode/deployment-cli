FROM node:14-alpine3.16

EXPOSE 3000

WORKDIR /app

COPY /node_modules node_modules

COPY /dist .

CMD ["node", "index.js"]