import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import HomeNavbar from '../component/HomeNavbar';
import axios from 'axios';
import UnauthorizedRequest from './UnauthorizedRequest';
import './Login.css';

const initialState = {
    id: "",
    password: "",
    error: ""
}

const infoUser = {
    id: "",
    role: "",
    token: ""
}


export default class Login extends Component {

    constructor(props) {
        super(props)
        this.state = initialState
    }

    capitalizeFirstLetter = (word) => {
        return word.charAt(0).toUpperCase() + word.slice(1);
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
    }

    handleSubmit = (event) => {
        event.preventDefault();

        if (this.props.match.params.role === "supplier") {
            axios.post('http://localhost:8080/auth/' + this.props.match.params.role + 'login', {
                id: this.state.id,
                password: this.state.password
            })
                .then(res => {
                    this.setState(initialState);
                    infoUser.id = res.data.id;
                    infoUser.role = this.props.match.params.role
                    infoUser.token = res.data.token;
                    localStorage.setItem('info', JSON.stringify(infoUser))
                    this.setState({
                        id: this.state.id,
                        password: this.state.password,
                        error: ""
                    })
                    this.props.history.push('/supplierdetails')
                })
                .catch(err => {
                    console.log(err.response);
                    this.setState({ error: "Unauthorized request..." })
                })
        } else {
            axios.post('http://localhost:8080/auth/' + this.props.match.params.role + 'login', {
                id: this.state.id,
                password: this.state.password
            })
                .then(res => {
                    this.setState(initialState);
                    infoUser.id = res.data.id;
                    infoUser.role = this.props.match.params.role;
                    infoUser.token = res.data.token;
                    localStorage.setItem('info', JSON.stringify(infoUser))
                    this.setState({
                        id: this.state.id,
                        password: this.state.password,
                        error: ""
                    })
                    this.props.history.push('/admindetails')
                })
                .catch(err => {
                    console.log(err.response);
                    this.setState({ error: "Unauthorized request..." })
                })
        }

    }

    render() {
        return (

            <div>
                <HomeNavbar />
                {this.state.error ? <UnauthorizedRequest /> : null}

                <div className="container">
                    <form onSubmit={this.handleSubmit}>
                        <div className="form-inner">
                            <h2>Login</h2>
                            <div className="form-group">
                                <label htmlFor="id">{this.capitalizeFirstLetter(this.props.match.params.role)} ID: </label>
                                <input type="number"
                                    className="form-control"
                                    placeholder="Enter ID"
                                    name="id"
                                    onChange={this.handleChange} />
                            </div>
                            <div className="form-group">
                                <label to="id">{this.capitalizeFirstLetter(this.props.match.params.role)} Password: </label>
                                <input type="password"
                                    className="form-control"
                                    placeholder="Enter password"
                                    name="password"
                                    onChange={this.handleChange} />
                            </div>
                            <button type="submit"
                                className="btn btn-primary"
                                id="button" >Login</button>
                            <div className="links">
                                <Link to="/" >Go back</Link>
                                {this.props.match.params.role === "supplier" ?
                                    (
                                        <Link to="/signup">Create an account</Link>
                                    ) : null}

                            </div>
                        </div>

                    </form>
                </div>











                {/* <div className="container-fluid core">
                    <div className="row">
                        <div className="col-lg-4 col-md-4"></div>

                        <div className="col-lg-4 col-md-4 col-sm-12">
                            <div className="card">
                                <div className="form-group content">
                                    <h2>Login</h2>
                                    <form onSubmit={this.handleSubmit}>
                                        <div>
                                            <label htmlFor="id">Supplier ID: </label>
                                            <input type="number"
                                                className="form-control"
                                                placeholder="Enter ID"
                                                name="id"
                                                onChange={this.handleChange} />
                                        </div>
                                        <div>
                                            <label to="id">Supplier Password: </label>
                                            <input type="password"
                                                className="form-control"
                                                placeholder="Enter password"
                                                name="password"
                                                onChange={this.handleChange} />
                                        </div>
                                        <div className="d-flex justify-content-between top">
                                            <button type="submit"
                                                className="btn btn-primary"
                                                id="button" >Login</button>
                                        </div>

                                        <div className="links">
                                            <Link to="/" >Go back</Link>
                                            <Link to="/signup">Create an account</Link>
                                        </div>

                                    </form>
                                </div>
                            </div>

                        </div >
                    </div>

                </div> */}

            </div >

        )
    }
}
