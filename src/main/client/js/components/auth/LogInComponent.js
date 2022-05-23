import React, {useEffect, useRef, useState} from "react";
import {getToken} from "../helpers";

let config = {headers: {'X-CSRF-TOKEN': getToken()}};

export function LogInComponent() {

    function login() {
        //TODO
    }

    function register() {
        window.location.pathname = "/register";
    }

    return (
        <div className="authorization_overlay display-none">
            <div className="authorization">
                <span className="authorization__login">Login</span>
                <div className="authorization__input" data-validate="Login is required">
                    <input type="text" name="login" placeholder="Login"/>
                </div>
                <div className="authorization__input" data-validate="Password is required">
                    <input type="password" name="password" placeholder="Password"/>
                </div>
                <div className="errors display-none">
                    <span id="errorsText"></span>
                </div>
                <div className="authorization__buttons">
                    <button className="authorization__button authorization__button--login" id="loginButton"
                            onClick={login}>
                        Login
                    </button>
                    <button className="authorization__button authorization__button--register" id="registerButton"
                            onClick={register}>
                        Register
                    </button>
                </div>
            </div>
        </div>
    )
}