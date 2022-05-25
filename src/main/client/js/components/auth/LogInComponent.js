import React, {useEffect, useRef, useState} from "react";
import {getToken} from "../helpers";

let config = {headers: {'X-CSRF-TOKEN': getToken()}};

export function LogInComponent() {

    const [state, setState] = useState({login: "", pass: ""});

    function login(ev) {
        ev.preventDefault();
        let requ = window.location.origin + "/login";
        const l = {
            custom_username: state.login,
            custom_password: state.pass
        }
        let cfg = {
            method: 'POST',
            body: JSON.stringify(l),
            headers: {
                'X-CSRF-TOKEN':
                    getToken(),
                'Content-type':
                    'application/json'
            }
        }
        fetch(requ, cfg).then(resp => {
            window.location.replace(resp.url);
        }).catch(console.warn)
    }

    function register() {
        window.location.pathname = "/register";
    }

    return (
        <div className="authorization_overlay">
            <div className="authorization">
                <form action="/login" method="post">
                    <span className="authorization__login">Login</span>
                    <div className="authorization__input" data-validate="Login is required">
                        <input type="text" name="custom_username" placeholder="Login" value={state.login}
                               onChange={ev => setState({
                                   login: ev.target.value,
                                   pass: state.pass
                               })}/>
                    </div>
                    <div className="authorization__input" data-validate="Password is required">
                        <input type="password" name="custom_password" placeholder="Password" value={state.pass}
                               onChange={ev => setState({
                                   login: state.login,
                                   pass: ev.target.value
                               })}/>
                    </div>
                    <input type="hidden" name="X-CSRF-TOKEN" value={getToken()}/>
                    <div className="errors display-none">
                        <span id="errorsText"></span>
                    </div>
                    <div className="authorization__buttons">
                        <button className="authorization__button authorization__button--login" id="loginButton"
                                type="submit">
                            Login
                        </button>
                        <button className="authorization__button authorization__button--register" id="registerButton"
                                onClick={register}>
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}