import '@testing-library/cypress'


describe("stations_tests", ()=> {
    it("renders correctly", ()=> {
        cy.visit("/");
    })

    /* ==== Test Created with Cypress Studio ==== */

    /* ==== Test Created with Cypress Studio ==== */
    it('Add_station_test', ()=> {
        /* ==== Generated with Cypress Studio ==== */
        cy.login('admin@ts.com', 'Admin1111')
        cy.visit('/stations')
        cy.get('[style="height: 510px; width: 100%; padding: 20px;"] > :nth-child(1) > .MuiBox-root > .MuiButton-root').click();
        cy.get('#name').clear();
        cy.get('#name').type('Amsterdam Centraal');
        cy.get('#code').clear();
        cy.get('#code').type('AMS');
        cy.get('#city').clear();
        cy.get('#city').type('Amsterdam');
        cy.get('.MuiDialogActions-root > :nth-child(2)').click();
        /* ==== End Cypress Studio ==== */
    });

    /* ==== Test Created with Cypress Studio ==== */

    /* ==== Test Created with Cypress Studio ==== */
    it('test_delete_station', function() {
        /* ==== Generated with Cypress Studio ==== */
        cy.login('admin@ts.com', 'Admin1111');
        cy.visit('/stations');
        cy.get('[data-field="Actions"] > .MuiDataGrid-columnHeaderDraggableContainer > .MuiDataGrid-columnHeaderTitleContainer').click().type('{downarrow}').focused().tab().click();
        /* ==== End Cypress Studio ==== */
    });
})