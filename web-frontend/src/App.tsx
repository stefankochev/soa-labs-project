import React from 'react';
import './App.css'
import Home from './components/Home'
import ProtectedRoute from './components/ProtectedRoute'
import Profile from './components/Profile'
import keycloak from './keycloak'
import Nav from './components/Nav'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

const App = () => {

	return (
		<ReactKeycloakProvider authClient={keycloak}>
			<React.StrictMode>
				<BrowserRouter>
					<Nav />
					<Routes>
						<Route path="/" element={<Home />} />
						<Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
					</Routes>
				</BrowserRouter>
			</React.StrictMode>
		</ReactKeycloakProvider>
	)
}

export default App
