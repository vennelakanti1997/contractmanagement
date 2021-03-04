import React from 'react'
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';

export const SupplierSidebarData = [
    {
        title: 'Home',
        path: '/supplierdetails',
        icon: <AiIcons.AiFillHome />,
        className: 'nav-text'
    },
    {
        title: 'View Requirement',
        path: '/viewreqsupplier',
        icon: <FaIcons.FaCartPlus />,
        className: 'nav-text'
    },
    {
        title: 'View My Proposals',
        path: '/viewsupplierproposals',
        icon: <IoIcons.IoMdPeople />,
        className: 'nav-text'
    },
    {
        title: 'View My Contracts',
        path: '/viewcontracts',
        icon: <IoIcons.IoMdPeople />,
        className: 'nav-text'
    },
    {
        title: 'View Amenities',
        path: '/amenities',
        icon: <FaIcons.FaCartPlus />,
        className: 'nav-text'
    },
    {
        title: 'Manage Delivery Service',
        path: '/managedelivery',
        icon: <FaIcons.FaCartPlus />,
        className: 'nav-text'
    }
    // {
    //     title: 'Generate Report',
    //     path: '/reports',
    //     icon: <AiIcons.AiFillHome />,
    //     className: 'nav-text'
    // }

]


