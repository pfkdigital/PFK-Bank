# Start from the official Node.js LTS base image
FROM node:lts-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json before other files
# Utilise Docker cache to save re-installing dependencies if unchanged
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy all files
COPY . .

# Build the Next.js application
RUN npm run build

# Expose the listening port
EXPOSE 3000

# Run npm start to start the server
CMD [ "npm", "start" ]