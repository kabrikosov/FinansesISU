import React, {useState, useEffect} from "react";
import {strings} from "../Strings";
import {getToken} from "../helpers";
import {useParams} from "react-router-dom";
const requ = window.location.origin + '/api/products/add';

let postHeaders = {'X-CSRF-TOKEN': getToken(), 'Content-Type': 'application/json'};
const getConfig = {headers: {'X-CSRF-TOKEN': getToken()}};

export function AddViewEditProduct() {
    const [state, setState] = useState({
        name: '',
        quantity: 1,
        price: 1,
        date: new Date(),
        category: null,
        subscription: null,
        user: 0,
    });
    const [data, setData] = useState({categories: [], subscriptions: []});
    const [status, setStatus] = useState({loaded: false});
    let { id } = useParams();

    function saveProduct(e) {
        e.preventDefault();
        const product = {
            name: state.name,
            quantity: state.quantity,
            price: state.price,
            date: state.date.toISOString().substr(0, 16),
            category: {
                id: state.category
            },
            user: {
                id: state.user
            }
        };
        if (state.subscription != null){
            product.subscription = {id: state.subscription};
        } else product.subscription = null

        let method = (id == null) ? 'POST' : 'PUT';


        fetch(requ, {
            body: JSON.stringify(product),
            headers: postHeaders,
            method: method
        })
            .then(resp => {
                console.log(resp)
            })
            .catch(err => {
                console.log(err)
            });
    }

    function noParamsSpecified(){
        let req1 = window.location.origin + "/api/categories/all"
        let req2 = window.location.origin + "/api/subscriptions/all"
        let req3 = window.location.origin + "/api/user/getId"

        let resp1 = fetch(req1, getConfig).then(resp => resp.json()).catch(console.warn);
        let resp2 = fetch(req2, getConfig).then(resp => resp.json()).catch(console.warn);
        let resp3 = fetch(req3, getConfig).then(resp => resp.json()).catch(console.warn);
        Promise.all([resp1, resp2, resp3])
            .then(response => {
                setData({categories: response[0], subscriptions: response[1]})
                setState({
                    name: state.name,
                    quantity: state.quantity,
                    price: state.price,
                    date: new Date(),
                    category: state.category,
                    subscription: state.subscription,
                    user: response[2],
                });
                if (!status.loaded){
                    setStatus({loaded: true});
                }
            });
    }

    function paramsAreSpecified(){
        let req1 = window.location.origin + "/api/products/" + id;
        let req2 = window.location.origin + "/api/categories/all"
        let req3 = window.location.origin + "/api/subscriptions/all"

        let resp1 = fetch(req1, getConfig).then(resp => resp.json()).catch(console.warn);
        let resp2 = fetch(req2, getConfig).then(resp => resp.json()).catch(console.warn);
        let resp3 = fetch(req3, getConfig).then(resp => resp.json()).catch(console.warn);

        Promise.all([resp1, resp2, resp3])
            .then(resp => {
                setState({
                    name: resp[0].name,
                    quantity: resp[0].quantity,
                    price: resp[0].price,
                    date: new Date(resp[0].date),
                    category: resp[0].category,
                    user: resp[0].user
                });
                setData({
                    categories: resp[1],
                    subscriptions: resp[2]
                });
                if (!status)
                    setStatus(!status);
            })
            .catch(console.warn);
    }


    useEffect(() => {
        if (id == null)
            noParamsSpecified();
        else paramsAreSpecified();
    }, [status])

    return (
        <div className="content__add-form">
            <form>
                <div className="form__field">
                    <label htmlFor="name">{strings[lang].model.name}</label>
                    <input
                        id="name"
                        type="text"
                        name="name"
                        value={state.name}
                        onChange={e => setState({
                            name: e.target.value,
                            quantity: state.quantity,
                            price: state.price,
                            date: state.date,
                            category: state.category,
                            subscription: state.subscription,
                            user: state.user,
                        })}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="quantity">{strings[lang].model.quantity}</label>
                    <input id="quantity" type="number" name="quantity"
                           value={state.quantity}
                           onChange={e => setState({
                               name: state.name,
                               quantity: e.target.value,
                               price: state.price,
                               date: state.date,
                               category: state.category,
                               subscription: state.subscription,
                               user: state.user,
                           })}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="price">{strings[lang].model.price}</label>
                    <input id="price" type="number" name="price"
                           value={state.price}
                           onChange={e => setState({
                               name: state.name,
                               quantity: state.quantity,
                               price: e.target.value,
                               date: state.date,
                               category: state.category,
                               subscription: state.subscription,
                               user: state.user,
                           })}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="date">{strings[lang].model.date}</label>
                    <input id="date" type="datetime-local" name="date"
                           value={state.date.toISOString().substr(0, 16)}
                           onChange={e => setState({
                               name: state.name,
                               quantity: state.quantity,
                               price: state.price,
                               date: e.target.value,
                               category: state.category,
                               subscription: state.subscription,
                               user: state.user,
                           })}/>
                </div>
                <div className="form__field">
                    <label htmlFor="category">{strings[lang].model.category}</label>
                    <select id="category" name="category"
                            value={state.category}
                            onChange={e => setState({
                                name: state.name,
                                quantity: state.quantity,
                                price: state.price,
                                date: state.date,
                                category: e.target.value,
                                subscription: state.subscription,
                                user: state.user,
                            })}>
                        <option selected={state.category == null} value={null}>{strings[lang].chooseOption}</option>
                        {data.categories.map(el =>
                            <option selected={state.category === el.id} value={el.id}>{el.name}</option>
                        )}
                    </select>
                </div>
                <div className="form__field">
                    <label htmlFor="subscription">{strings[lang].model.subscription}</label>
                    <select id="subscription" name="subscription"
                            value={state.subscription}
                            onChange={e => setState({
                                name: state.name,
                                quantity: state.quantity,
                                price: state.price,
                                date: state.date,
                                category: state.category,
                                subscription: e.target.value,
                                user: state.user,
                            })}>
                        <option selected={state.subscription === null} value={null}>{strings[lang].chooseOption}</option>
                        {data.subscriptions.map(el =>
                            <option selected={state.subscription === el.id} value={el.id}>{el.name}</option>
                        )}
                    </select>
                </div>
                <button onClick={saveProduct} className="form__submit-button"
                        type="submit">{strings[lang].forms.submit}</button>
            </form>
        </div>)
}