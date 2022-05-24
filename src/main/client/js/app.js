import React from 'react'
import {createRoot} from 'react-dom/client'
import {FinanceCircle} from "./components/FinanceCircle";
import {ProductsTable} from "./components/ProductsTable";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {AddProduct} from "./components/add/AddProduct";
import {StatisticsComponent} from "./components/StatisticsComponent";
import {LogInComponent} from "./components/auth/LogInComponent";
import {RegisterComponent} from "./components/auth/Registration";

const root = createRoot(document.getElementById("app"));
root.render(
    <Router>
        <Routes>
            <Route exact path="/" element={<StatisticsComponent />}/>
            <Route path="home" element={<StatisticsComponent />}/>
            <Route path="/login" element={<LogInComponent />} />
            <Route path="/register" element={<RegisterComponent />} />
            <Route path="products">
                <Route exact path="" element={<ProductsTable />} />
                <Route path="add" element={<AddProduct />}/>
            </Route>
        </Routes>
    </Router>
)