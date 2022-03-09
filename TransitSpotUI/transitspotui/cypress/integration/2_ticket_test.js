import '@testing-library/cypress'

describe("ticket_tests", ()=> {
    it("renders correctly", ()=> {
        cy.visit("/");
    })

    /* ==== Test Created with Cypress Studio ==== */

    /* ==== Test Created with Cypress Studio ==== */


    /* ==== Test Created with Cypress Studio ==== */
    it('Find_path_not_logged_in_test', ()=> {
        /* ==== Generated with Cypress Studio ==== */
        cy.visit('/');
        cy.get('div.MuiSelect-root').eq(0).type('{downarrow}{downarrow}{downarrow}{enter}')
        cy.get('div.MuiSelect-root').eq(1).type('{downarrow}{downarrow}{enter}')
        cy.get('[data-testid="CalendarIcon"]').click();
        cy.get(':nth-child(4) > :nth-child(6) > .MuiButtonBase-root').click();
        cy.get('.MuiButton-root').click();
        /* ==== End Cypress Studio ==== */
    });


    /* ==== Test Created with Cypress Studio ==== */
    it('test_buy_ticket_logged_in', function() {
        /* ==== Generated with Cypress Studio ==== */
        cy.login('transitspotbv@gmail.com', 'User1111');
        cy.visit('/');
        cy.get('div.MuiSelect-root').eq(0).type('{downarrow}{downarrow}{downarrow}{enter}')
        cy.get('div.MuiSelect-root').eq(1).type('{downarrow}{downarrow}{enter}')
        cy.get('[data-testid="CalendarIcon"]').click();
        cy.get(':nth-child(4) > :nth-child(6) > .MuiButtonBase-root').click();
        cy.get('.MuiButton-root').click();
        cy.get('[data-cy="submit_next_step"]').click();
        cy.get('#firstName').clear();
        cy.get('#firstName').type('Bohdan');
        cy.get('#lastName').clear();
        cy.get('#lastName').type('Tymofieienko');
        cy.get('#document_nr').clear();
        cy.get('#document_nr').type('AAA1');
        cy.get('[data-cy="submit_next_step"]').click();
        /* ==== End Cypress Studio ==== */
    });
})