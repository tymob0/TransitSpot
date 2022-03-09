import '@testing-library/cypress'

describe("routes_tests", ()=> {
    it("renders correctly", ()=> {
        cy.visit("/");
    })

    /* ==== Test Created with Cypress Studio ==== */
    it('Add_route_test_test', ()=> {
        /* ==== Generated with Cypress Studio ==== */
        cy.login('admin@ts.com', 'Admin1111')
        cy.visit('/Routes');
        cy.get('[style="height: 510px; width: 100%; padding: 20px;"] > :nth-child(1) > .MuiBox-root > .MuiButton-root').click();
        cy.get('#name').clear();
        cy.get('#name').type('Amsterdam Centraal');
        cy.get('#name').clear();
        cy.get('#name').type('Eindhoven Centraal-Groningen Centraal');
        cy.get('#code').clear();
        cy.get('#code').type('EIN-GRO1');
        cy.get('#price').clear();
        cy.get('#price').type('20.0');
        cy.get('#duration').clear();
        cy.get('#duration').type('120');
        cy.get('div.MuiSelect-root').eq(0).type('{downarrow}{downarrow}{downarrow}{enter}')
        cy.get('div.MuiSelect-root').eq(1).type('{downarrow}{downarrow}{enter}')
        cy.get('.MuiDialogContent-root').click();
        cy.get('.MuiDialogActions-root > :nth-child(2)').click();
        /* ==== End Cypress Studio ==== */
    });
})