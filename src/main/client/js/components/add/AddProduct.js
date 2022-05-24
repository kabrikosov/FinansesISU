import React, {useRef, useState, useEffect} from "react";
import {strings} from "../Strings";
import {getToken} from "../helpers";
const requ = window.location.origin + '/api/products/add';

let config = {headers: {'X-CSRF-TOKEN': getToken(), 'Content-Type': 'application/json'}};

export function AddProduct() {
    const [name, setName] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [price, setPrice] = useState(1);
    const [date, setDate] = useState(new Date());
    const [category, setCategory] = useState(null);
    const [subscription, setSubscription] = useState(null);
    const [data, setData] = useState({categories: [], subscriptions: []});
    const [status, setStatus] = useState({loaded: false});


    function saveProduct(e) {
        e.preventDefault();
        const product = {
            name: name,
            quantity: quantity,
            price: price,
            date: date.toISOString().substr(0, 16),
            category: category,
            subscription: subscription
        };
        fetch(requ, {
            body: JSON.stringify(product),
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST'
        })
            .then(resp => {
                console.log(resp)
            })
            .catch(err => {
                console.log(err)
            });
    }

    useEffect(() => {
        let req1 = window.location.origin + "/api/categories/all"
        let req2 = window.location.origin + "/api/subscriptions/all"

        let resp1 = fetch(req1, config).then(resp => resp.json());
        let resp2 = fetch(req2, config).then(resp => resp.json());
        Promise.all([resp1, resp2])
            .then(response => {
                setData({categories: response[0], subscriptions: response[1]})
                setDate(new Date());
                if (!status.loaded){
                    setStatus({loaded: true});
                }
            })
            .catch(console.log);
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
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="quantity">{strings[lang].model.quantity}</label>
                    <input id="quantity" type="number" name="quantity"
                           value={quantity}
                           onChange={e => setQuantity(e.target.value)}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="price">{strings[lang].model.price}</label>
                    <input id="price" type="number" name="price"
                           value={price}
                           onChange={e => setPrice(e.target.value)}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="date">{strings[lang].model.date}</label>
                    <input id="date" type="datetime-local" name="date"
                           value={date.toISOString().substr(0, 16)}
                           onChange={e => setDate(e.target.value)}/>
                </div>
                <div className="form__field">
                    <label htmlFor="category">{strings[lang].model.category}</label>
                    <select id="category" name="category"
                            value={category}
                            onChange={e => setCategory(e.target.value)}>
                        <option selected value={null}>{strings[lang].chooseOption}</option>
                        {data.categories.map(el =>
                            <option value={el.id}>{el.name}</option>
                        )}
                    </select>
                </div>
                <div className="form__field">
                    <label htmlFor="subscription">{strings[lang].model.subscription}</label>
                    <select id="subscription" name="subscription"
                            value={subscription}
                            onChange={e => setSubscription(e.target.value)}>
                        <option selected value={null}>{strings[lang].chooseOption}</option>
                        {data.subscriptions.map(el =>
                            <option value={el.id}>{el.name}</option>
                        )}
                    </select>
                </div>
                <button onClick={saveProduct} className="form__submit-button"
                        type="submit">{strings[lang].submit}</button>
            </form>
        </div>)
}