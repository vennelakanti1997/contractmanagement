import React, { Component } from 'react'
import axios from 'axios';
import HomeNavbar from '../component/HomeNavbar';
import './Signup.css'
import { Link } from 'react-router-dom'

const initialState = {
    name: "",
    password: "",
    contactNumber: "",
    address: "",
    type: "",
    nameError: "",
    passwordError: "",
    contactNumberError: "",
    addressError: "",
    typeError: ""
}

export let supplierId = ""
export let supplierName = ""
export let supplierContactNumber = ""
export let supplierAddress = ""
export let supplierType = ""

export default class Signup extends Component {

    state = initialState

    handleSubmit = (event) => {
        event.preventDefault();
        const isValid = this.validate();
        if (isValid) {
            axios.post('http://localhost:8080/auth/signup', {
                name: this.state.name,
                password: this.state.password,
                contactNumber: this.state.contactNumber,
                address: this.state.address,
                typeId: this.state.type
            })
                .then(res => {
                    console.log(this.state)
                    console.log(res.data);
                    supplierId = res.data.id;
                    supplierName = res.data.name;
                    supplierContactNumber = res.data.contactNumber;
                    supplierAddress = res.data.address;
                    supplierType = this.state.type;
                    this.props.history.push('/userdetails')
                })
                .catch(err => {
                    console.log(err.response)
                })
        }



    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
        console.log(event.target.name);
    }

    validate = () => {
        const regex = /[!@#$%^&*]/
        const result = regex.test(this.state.password);

        let nameError = "";
        let passwordError = "";
        let contactNumberError = "";
        let addressError = "";
        let typeError = "";

        if (this.state.name.length >= 50 || this.state.name.length <= 4) {
            nameError = "Minimum length 4, maxmimum length 50"
            this.setState({ nameError })
            return false
        } else {
            this.setState({
                nameError: "",
            })
        }
        if (this.state.password.length >= 50 || this.state.password <= 4 || !result || this.state.password.includes(" ")) {
            passwordError = "Minimum length 4, maxmimum length 50, no white spaces and atleast 1 special character"
            this.setState({ passwordError })
            return false
        } else {
            this.setState({
                passwordError: "",
            })
        }

        if (this.state.contactNumber.length !== 10) {
            contactNumberError = "Contact Number should be of 10 digits"
            this.setState({ contactNumberError })
            return false
        } else {
            this.setState({
                contactNumberError: "",
            })
        }

        if (this.state.address.length >= 50 || this.state.address.length <= 3) {
            addressError = "Minimum length 3, maxmimum length 50"
            this.setState({ addressError })
            return false
        } else {
            this.setState({
                addressError: "",
            })
        }

        if (this.state.type.length === 0) {
            typeError = "Supplier must have a type"
            this.setState({ typeError })
            return false
        } else {
            this.setState({
                typeError: "",
            })
        }

        return true
    }


    render() {
        return (
            <div>
                <HomeNavbar />

                <div className="container">

                    <form onSubmit={this.handleSubmit}>
                        <div className="form-inner">
                            <h2>Signup Form</h2>
                            <div className="form-group">
                                <label htmlFor="name">Name</label>
                                <input
                                    type="text"
                                    id="name"
                                    className="form-control"
                                    name="name"
                                    placeholder="enter your name..."
                                    value={this.state.name}
                                    onChange={this.handleChange} />
                                <div className="error">{this.state.nameError}</div>
                            </div>
                            <div className="form-group">
                                <div>
                                    <label htmlFor="password">Password</label>
                                    <input
                                        type="password"
                                        id="password"
                                        className="form-control"
                                        name="password"
                                        placeholder="enter your password..."
                                        value={this.state.password}
                                        onChange={this.handleChange} />
                                    <div className="error">{this.state.passwordError}</div>
                                </div>
                            </div>
                            <div className="form-group">
                                <div>
                                    <label htmlFor="contactNumber">Contact Number</label>
                                    <input
                                        type="number"
                                        id="contactNumber"
                                        className="form-control"
                                        name="contactNumber"
                                        placeholder="contact number..."
                                        onChange={this.handleChange}
                                        value={this.state.contactNumber} />
                                    <div className="error">{this.state.contactNumberError}</div>
                                </div>
                            </div>
                            <div className="form-group">
                                <div>
                                    <label htmlFor="address" >Address</label>
                                    <textarea
                                        id="address"
                                        name="address"
                                        className="form-control"
                                        onChange={this.handleChange}
                                        value={this.state.address}
                                        style={{ "verticalAlign": "middle" }} />
                                    <div className="error">{this.state.addressError}</div>
                                </div>
                            </div>
                            <div className="form-group">
                                <div>
                                    <label style={{ "padding": "10px 10px 10px 0" }}>Supplier Type</label>
                                    <select
                                        name="type"
                                        value={this.state.type}
                                        onChange={this.handleChange} >
                                        <option selected>---Select type---</option>
                                        <option value="1">Type 1</option>
                                        <option value="2">Type 2</option>
                                        <option value="3">Type 3</option>
                                    </select>
                                    <div className="error">{this.state.typeError}</div>
                                </div>
                            </div>

                            <button type="submit"
                                className="btn btn-primary" >Signup</button>

                            <Link to="/login/supplier" className="goBackLink">Go back</Link>
                        </div>

                    </form>
                </div>

            </div>
        )
    }
}
