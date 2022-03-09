import '@testing-library/cypress'

describe("user_test", ()=> {
    it("renders correctly", ()=> {
        cy.visit("/");
    })

    /* ==== Test Created with Cypress Studio ==== */
    it('Authorize_test', ()=> {
        /* ==== Generated with Cypress Studio ==== */
        cy.visit('/LogIn');
        cy.get('#email').clear();
        cy.get('#email').type('transitspotbv@gmail.com');
        cy.get('#password').clear();
        cy.get('#password').type('User1111');
        cy.get('.MuiButton-root').click();
        cy.get(':nth-child(2) > .MuiBox-root > .MuiButtonBase-root').click();
        cy.get('.MuiBackdrop-root').click();
        /* ==== End Cypress Studio ==== */
    });

     /* ==== Test Created with Cypress Studio ==== */
     it('Authorize_wrong_test', function() {
        /* ==== Generated with Cypress Studio ==== */
        cy.visit('http://localhost:3000/LogIn');
        cy.get('#email').clear();
        cy.get('#email').type('fdasfdsafads');
        cy.get('#password').clear();
        cy.get('#password').type('dsafasdfasdf');
        cy.get('.MuiButton-root').click();
        /* ==== End Cypress Studio ==== */
    });


    /* ==== Test Created with Cypress Studio ==== */
    it('SignUp_test_test', ()=> {
        /* ==== Generated with Cypress Studio ==== */
        cy.visit('http://localhost:3000/LogIn');
        cy.get('.css-13i4rnv-MuiGrid-root > .MuiTypography-root').click();
        cy.get('#email').clear();
        cy.get('#email').type('bohdan@gmail.com');
        cy.get('#password').clear();
        cy.get('#password').type('Admin11112');
        cy.get('#password_rpt').clear();
        cy.get('#password_rpt').type('Admin11112');
        cy.get('#First_Name').clear();
        cy.get('#First_Name').type('Bohdan');
        cy.get('#Last_Name').clear();
        cy.get('#Last_Name').type('Tymofieienko');
        cy.get('.MuiButton-root').click();
        cy.get('#email').clear();
        cy.get('#email').type('bohdan@gmail.com');
        cy.get('#password').clear();
        cy.get('#password').type('Admin11112');
        cy.get('.MuiButton-root').click();
        /* ==== End Cypress Studio ==== */
    });

    /* ==== Test Created with Cypress Studio ==== */


    /* ==== Test Created with Cypress Studio ==== */
    it('logout', function() {
        /* ==== Generated with Cypress Studio ==== */
        cy.visit('localhost:3000/LogIn');
        cy.get('#email').clear();
        cy.get('#email').type('transitspotbv@gmail.com');
        cy.get('#password').clear();
        cy.get('#password').type('User1111');
        cy.get('.MuiButton-root').click();
        cy.get('[data-testid="PersonIcon"]').click();
        cy.get('.MuiList-root > :nth-child(4) > a').click();
        /* ==== End Cypress Studio ==== */
    });

   
})