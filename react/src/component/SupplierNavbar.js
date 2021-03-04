import React, { useState } from 'react';
import './SupplierNavbar.css';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import { Link, useHistory } from 'react-router-dom';
import { SupplierSidebarData } from './SupplierSidebarData';
import { IconContext } from 'react-icons';
import { Nav, NavDropdown } from 'react-bootstrap';
import axios from 'axios'

function SupplierNavbar() {
    const [sidebar, setSidebar] = useState(false)
    const supplierData = JSON.parse(localStorage.getItem('info'))
    const showSidebar = () => setSidebar(!sidebar)

    const [supplierName, setSupplierName] = useState();

    axios.get('http://localhost:8080/auth/suppliervalidate', {
        headers: {
            'Authorization': `Bearer ${supplierData.token}`
        }
    })
        .then(res => {
            setSupplierName(res.data.name)
        })
        .catch(err => {
            console.log(err)
        })


    const history = useHistory();

    const handleLogout = () => {
        localStorage.clear()
        history.push('/')
    }


    return (
        <>
            <IconContext.Provider value={{ color: '#fff' }}>
                <div className="navbar">
                    <Link to="#" className='menu-bars'>
                        <FaIcons.FaBars onClick={showSidebar} />
                    </Link>
                    <h1 className="navbar-logo">Contract Management<i className="fab fa-react"></i></h1>
                    {localStorage ? (
                        <Nav>
                            <NavDropdown
                                title={supplierName}
                                style={{
                                    "backgroundColor": "black",
                                    "marginRight": "12px",
                                    "fontWeight": "bold",
                                    "borderRadius": "10%"
                                }}>
                                <NavDropdown title="Generate Reports">
                                    <NavDropdown.Item href="/listcontracts">Contracts</NavDropdown.Item>
                                    <NavDropdown.Item href="/listproposalreport">Proposals</NavDropdown.Item>
                                </NavDropdown>
                                <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    ) : null}

                </div>
                <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
                    <ul className='nav menu-items' onClick={showSidebar}>
                        <li className='navbar-toggle'>
                            <Link to="#" className='menu-bars'>
                                <AiIcons.AiOutlineClose />
                            </Link>
                        </li>
                        {SupplierSidebarData.map((item, index) => {
                            return (
                                <li key={index} className={item.className}>
                                    <Link to={item.path}>
                                        {item.icon}
                                        <span>{item.title}</span>
                                    </Link>
                                </li>
                            )
                        })}
                    </ul>
                </nav>
            </IconContext.Provider>
        </>

    )
}

export default SupplierNavbar
