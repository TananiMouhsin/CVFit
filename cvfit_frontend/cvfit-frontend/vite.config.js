import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  esbuild: {
    jsx: 'automatic',
    jsxInject: `import React from 'react'`, // Optional, if you're using React 17 JSX Transform
  },
  build: {
    target: 'esnext', // This ensures compatibility with modern JavaScript features
    rollupOptions: {
      input: 'index.html', // Make sure entry points are correctly defined
    },
  },
})
