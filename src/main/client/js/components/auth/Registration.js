import React, {useState} from "react";
import {getToken} from "../helpers";


export function RegisterComponent() {
    const [state, setState] = useState({
        login: null,
        pass: null,
        monthlyIncome: 0,
        cashAvailable: 0,
        name: null,
    });

    function login() {
        window.location.pathname = "/login";
    }

    function error(err) {
        console.warn(err);
    }

    function register() {
        let requ = window.location.origin + "/api/register";
        let user = {
            login: state.login,
            password: state.pass,
            monthlyIncome: state.monthlyIncome,
            cashAvailable: state.cashAvailable,
            name: state.name,
        }
        let str = JSON.stringify(user);
        fetch(requ, {
            method: 'POST',
            body: str,
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': getToken(),
            }
        })
            .then(resp => {
                if (resp.status === 200)
                    window.location.pathname = "/home"
            })
            .catch(err => error(err));
    }


    return (
        <div className="authorization_overlay">
            <div className="authorization">
                <span className="authorization__login">Register</span>
                <form method="post" action="http://localhost:8080/register">
                    <div className="authorization__input" data-validate="Name is required">
                        <input type="text" name="name" placeholder="Name" value={state.name}
                               onChange={e => setState({
                                   login: state.login,
                                   pass: state.pass,
                                   monthlyIncome: state.monthlyIncome,
                                   cashAvailable: state.cashAvailable,
                                   name: e.target.value,
                               })}/>
                    </div>
                    <div className="authorization__input" data-validate="Login is required">
                        <input type="text" name="login" placeholder="Login" value={state.login}
                               onChange={e => setState({
                                   login: e.target.value,
                                   pass: state.pass,
                                   monthlyIncome: state.monthlyIncome,
                                   cashAvailable: state.cashAvailable,
                                   name: state.name,
                               })}/>
                    </div>
                    <div className="authorization__input">
                        <input type="number" name="monthlyIncome" placeholder="Monthly income"
                               value={state.monthlyIncome}
                               onChange={e => setState({
                                   login: state.login,
                                   pass: state.pass,
                                   monthlyIncome: e.target.value,
                                   cashAvailable: state.cashAvailable,
                                   name: state.name,
                               })}
                        />
                    </div>
                    <div className="authorization__input">
                        <input type="number" name="cashAvailable" placeholder="Cash available"
                               value={state.cashAvailable}
                               onChange={e => setState({
                                   login: state.login,
                                   pass: state.pass,
                                   monthlyIncome: state.monthlyIncome,
                                   cashAvailable: e.target.value,
                                   name: state.name,
                               })}
                        />
                    </div>
                    <div className="errors display-none">
                        <span id="errorsText"></span>
                    </div>
                    <div className="authorization__input" data-validate="Password is required">
                        <input type="password" name="password" placeholder="Password" value={state.pass}
                               onChange={e => setState({
                                   login: state.login,
                                   pass: e.target.value,
                                   monthlyIncome: state.monthlyIncome,
                                   cashAvailable: state.cashAvailable,
                                   name: state.name,
                               })}
                        />
                    </div>
                    <div className="authorization__buttons">
                        <button className="authorization__button authorization__button--login" id="loginButton"
                                onClick={login}>
                            Login
                        </button>
                        <button className="authorization__button authorization__button--register" id="registerButton"
                                type="submit">
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}