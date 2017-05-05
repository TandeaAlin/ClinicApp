import { FrontendA3Page } from './app.po';

describe('frontend-a3 App', () => {
  let page: FrontendA3Page;

  beforeEach(() => {
    page = new FrontendA3Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
