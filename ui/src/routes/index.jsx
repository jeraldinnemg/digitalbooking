import { Route, Routes } from "react-router-dom";
import { Home } from "../pages/home";
import { NotFound } from "../pages/notFound";
import Detail from "../pages/Detail";
import Header from "../components/Header/header";
import Footer from "../components/Footer/footer";
import { ProductList } from "../pages/productList";
import { Admin } from "../pages/admin";
import { Register } from "../pages/register";

import { Login } from "../pages/login";
import { SignUp } from "../pages/signUp";

import { Success } from "../pages/success";
import { CategoryList } from "../pages/categoryList";

export const AppRoutes = () => {
	return (
		<>
			<Header />
			<Routes>
				<Route
					path="/"
					element={<Home />}
				/>
				<Route
					path="/detalle/:id"
					element={<Detail />}
				/>
				<Route
					path="/admin"
					element={<Admin />}
				/>
				<Route
					path="/product-list"
					element={<ProductList />}
				/>
				<Route
					path="/register"
					element={<Register />}
				/>
				<Route
					path="/login"
					element={<Login />}
				/>
				<Route
					path="/signup"
					element={<SignUp />}
				/>
				<Route
					path="/register-success"
					element={<Success />}
				/>
				<Route
					path="*"
					element={<NotFound />}
				/>
			</Routes>
			<Footer />
		</>
	);
};
