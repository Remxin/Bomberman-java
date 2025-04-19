# Branch Naming Conventions for the Project

To maintain organization and ensure easy identification of the purpose of each branch, we have established a consistent branch naming system.

## General Guidelines

1. **Use lowercase letters** – Use lowercase letters for branch names to maintain consistency.
2. **Use hyphens** – Separate words in branch names with hyphens (`-`), not underscores (`_`).
3. **Avoid spaces** – Do not use spaces in branch names.
4. **Prefixes** – The branch name should contain a prefix indicating the type of task.

## Branch Types

1. **feature/** – For new features being developed.  
   Example: `feature/user-login`, `feature/new-homepage`.

2. **bugfix/** – For fixing bugs.  
   Example: `bugfix/login-issue`, `bugfix/save-error`.

3. **hotfix/** – For urgent fixes that need to be addressed immediately, typically in the production version.  
   Example: `hotfix/product-page-error`, `hotfix/cart-issue`.

4. **release/** – For preparing a version for release.  
   Example: `release/1.0.0`, `release/2.1.0`.

5. **chore/** – For minor tasks such as updating dependencies, refactoring code, etc.  
   Example: `chore/update-dependencies`, `chore/folder-structure-refactor`.

6. **docs/** – For changes in the documentation.  
   Example: `docs/installation-guide`, `docs/update-readme`.

## Branch Name Structure

Each branch name should start with the appropriate prefix and then include a short description of the branch's purpose.

**Examples:**
- `feature/add-search-option`
- `bugfix/fix-error-logs`
- `hotfix/urgent-api-fix`
- `release/2.0.1`
- `docs/update-system-requirements`

## Workflow

1. **Creating a Branch**  
   Every team member should create a branch based on the main branch (e.g., `main` or `develop`) to avoid working on an outdated version.

2. **Submitting Pull Requests**  
   After completing work on a branch, a Pull Request should be created to merge the branch into the main branch (e.g., `develop` or `main`). Before approving the Pull Request, make sure the code is tested and works correctly.

3. **Review and Merging**  
   After the Pull Request is reviewed by other team members, the branch can be merged into the main branch.

---

If you have any questions about branch names, feel free to consult with the team to maintain consistency and clarity.
