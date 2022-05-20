import React from 'react'
import {createRoot} from 'react-dom/client'
import {FinanceCircle} from "./components/FinanceCircle";
import {ProductsTable} from "./components/ProductsTable";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

const root = createRoot(document.getElementById("app"));
root.render(
    <Router>
        <Routes>
            <Route exact path="/" element={<FinanceCircle/>}> </Route>
            <Route path="/home" element={<FinanceCircle/>}> </Route>
            <Route path="/products" element={<ProductsTable/>}> </Route>
        </Routes>
    </Router>
)