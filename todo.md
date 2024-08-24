## TODO
- make bill generator more responsive.
- make table to store these info , sending all data through json. 

Here are the key fields that should be included in each type of invoice or billing document:

formats: [
                {
                    value: 'standard',
                    name: 'Standard',
                    heading: 'Invoice',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        customerExtraInfo:{
                            name:'',
                            show:false
                        },
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:true
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:false
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:true
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:true
                        } ,
                        discount:{
                            name:'',
                            show:true
                        } ,
                        taxAmount:{
                            name:'',
                            show:true
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'taxInvoice',
                    name: 'Tax Invoice',
                    heading: 'Tax Invoice',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        customerExtraInfo:{
                            name:'',
                            show:true
                        },
                        dueDate:{
                            name:'',
                            show:true
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:true
                        } ,
                        gstType:{
                            name:'',
                            show:true
                        } ,
                        gstRate:{
                            name:'',
                            show:true
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:true
                        } ,
                        discount:{
                            name:'',
                            show:true
                        } ,
                        taxAmount:{
                            name:'',
                            show:true
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:true
                        } 
                    }
                },
                {
                    value: 'commercialInvoice',
                    name: 'Export Invoice',
                    heading: 'Export Invoice',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:true
                        } ,
                        customerExtraInfo:{
                            name:'',
                            show:true
                        },
                        placeOfSupply:{
                            name:'',
                            show:true
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:false
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:true
                        } ,
                        discount:{
                            name:'',
                            show:true
                        } ,
                        taxAmount:{
                            name:'',
                            show:false
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:true
                        } 
                    }
                },
                {
                    value: 'timesheet',
                    name: 'TimeSheet Invoice',
                    heading: 'TimeSheet Invoice',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:true
                        } ,
                        customerExtraInfo:{
                            name:'',
                            show:true
                        },
                        placeOfSupply:{
                            name:'',
                            show:false
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:false
                        } ,
                        quantity:{
                            name:'',
                            show:false
                        } ,
                        rate:{
                            name:'',
                            show:false
                        } ,
                        discount:{
                            name:'',
                            show:false
                        } ,
                        taxAmount:{
                            name:'',
                            show:false
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'creditNote',
                    name: 'Credit Note',
                    heading: 'Credit Note',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:true
                        } ,
                        gstType:{
                            name:'',
                            show:true
                        } ,
                        gstRate:{
                            name:'',
                            show:true
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:true
                        } ,
                        discount:{
                            name:'',
                            show:true
                        } ,
                        taxAmount:{
                            name:'',
                            show:true
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'debitNote',
                    name: 'Debit Note',
                    heading: 'Debit Note',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:true
                        } ,
                        gstType:{
                            name:'',
                            show:true
                        } ,
                        gstRate:{
                            name:'',
                            show:true
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:true
                        } ,
                        discount:{
                            name:'',
                            show:true
                        } ,
                        taxAmount:{
                            name:'',
                            show:true
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'receiptVoucher',
                    name: 'Receipt Voucher',
                    heading: 'Receipt Voucher',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:false
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:false
                        } ,
                        quantity:{
                            name:'',
                            show:false
                        } ,
                        rate:{
                            name:'',
                            show:false
                        } ,
                        discount:{
                            name:'',
                            show:false
                        } ,
                        taxAmount:{
                            name:'',
                            show:false
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'paymentVoucher',
                    name: 'Payment Voucher',
                    heading: 'Payment Voucher',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:false
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:false
                        } ,
                        quantity:{
                            name:'',
                            show:false
                        } ,
                        rate:{
                            name:'',
                            show:false
                        } ,
                        discount:{
                            name:'',
                            show:false
                        } ,
                        taxAmount:{
                            name:'',
                            show:false
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'refundVoucher',
                    name: 'Refund Voucher',
                    heading: 'Refund Voucher',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:false
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:false
                        } ,
                        quantity:{
                            name:'',
                            show:false
                        } ,
                        rate:{
                            name:'',
                            show:false
                        } ,
                        discount:{
                            name:'',
                            show:false
                        } ,
                        taxAmount:{
                            name:'',
                            show:false
                        } ,
                        total:{
                            name:'',
                            show:true
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:false
                        } 
                    }
                },
                {
                    value: 'deliveryNote',
                    name: 'Delivery Challan',
                    heading: 'Delivery Challan',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:true
                        } ,
                        gstType:{
                            name:'',
                            show:false
                        } ,
                        gstRate:{
                            name:'',
                            show:false
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:false
                        } ,
                        discount:{
                            name:'',
                            show:false
                        } ,
                        taxAmount:{
                            name:'',
                            show:false
                        } ,
                        total:{
                            name:'',
                            show:false
                        } ,
                        notes:{
                            name:'',
                            show:true
                        } ,
                        shippingAddress:{
                            name:'',
                            show:true
                        } 
                    }
                },
                {
                    value: 'ewayBill',
                    name: 'E-Way bill',
                    heading: 'E-Way bill',
                    fields: {
                        customerName:{
                            name:'',
                            show:true
                        } ,
                        address:{
                            name:'',
                            show:true
                        } ,
                        invoiceDate:{
                            name:'',
                            show:true
                        } ,
                        dueDate:{
                            name:'',
                            show:false
                        } ,
                        placeOfSupply:{
                            name:'',
                            show:true
                        } ,
                        gstType:{
                            name:'',
                            show:true
                        } ,
                        gstRate:{
                            name:'',
                            show:true
                        } ,
                        quantity:{
                            name:'',
                            show:true
                        } ,
                        rate:{
                            name:'',
                            show:false
                        } ,
                        discount:{
                            name:'',
                            show:false
                        } ,
                        taxAmount:{
                            name:'',
                            show:true
                        } ,
                        total:{
                            name:'',
                            show:false
                        } ,
                        notes:{
                            name:'',
                            show:false
                        } ,
                        shippingAddress:{
                            name:'',
                            show:true
                        } 
                    }
                }
            ],

These fields ensure that the necessary legal and business information is captured for each type of billing document.
