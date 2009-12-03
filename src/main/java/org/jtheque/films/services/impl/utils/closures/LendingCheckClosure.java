package org.jtheque.films.services.impl.utils.closures;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.MailUtils;
import org.jtheque.films.IFilmsModule;
import org.jtheque.films.persistence.dao.able.IDaoFilms;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.utils.bean.Email;
import org.jtheque.utils.collections.Closure;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * A closure to check all lendings and react to retards.
 *
 * @author Baptiste Wicht
 */
public final class LendingCheckClosure implements Closure<Lending> {
    @Resource
    private ILendingsService lendingsService;

    @Logger
    private IJThequeLogger logger;

    @Resource
    private IFilmsModule filmsModule;

    @Resource
    private IDaoFilms daoFilms;

    /**
     * Construct a new LendingCheckClosure.
     */
    public LendingCheckClosure() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void execute(Lending lending) {
        if (lendingsService.isLate(lending, filmsModule.getConfiguration().getTimeBeforeAutomaticSend())) {
            if (filmsModule.getConfiguration().areMailSendAutomatically()) {
                sendMailToBorrower(lending);
            }

            Object[] objects = new Object[3];
            objects[0] = daoFilms.getFilm(lending.getTheOther()).getDisplayableText();
            objects[1] = lending.getThePerson().getDisplayableText();
            objects[2] = lending.getDate().toString();

            if (filmsModule.getConfiguration().alertWithMail()) {
                sendMailToUser(objects);
            }

            if (filmsModule.getConfiguration().alertWithDialog()) {
                Managers.getManager(IViewManager.class).displayText(
                        Managers.getManager(ILanguageManager.class).getMessage("lendings.dialogs.message", objects));
            }
        }
    }

    /**
     * Send an email to the user with the objects replaces for the message.
     *
     * @param objects The replaces for the message.
     */
    private void sendMailToUser(Object[] objects) {
        Email mail = new Email();

        mail.setFrom("JTheque@jtheque.com");
        mail.setMessage(Managers.getManager(ILanguageManager.class).getMessage("lendings.mail.user.message", objects));
        mail.setSubject(Managers.getManager(ILanguageManager.class).getMessage("lendings.mail.user.subject"));
        mail.setTo(new String[]{Managers.getCore().getConfiguration().getUserEmail()});

        try {
            MailUtils.send(mail, Managers.getCore().getConfiguration().getSmtpHost());
        } catch (MessagingException e) {
            Managers.getManager(IErrorManager.class).addError(new InternationalizedError("jtheque.errors.mail"));

            logger.error(e);
        }
    }

    /**
     * Send an email to the borrower. Use the lending to fill the message.
     *
     * @param lending The lending to use to fill the message.
     */
    private void sendMailToBorrower(Lending lending) {
        Email mail = new Email();

        mail.setFrom(Managers.getCore().getConfiguration().getUserEmail());

        String message = filmsModule.getConfiguration().getAutomaticMail();

        message = message.replaceAll("\\$\\{emprunteur:firstname\\}", lending.getThePerson().getFirstName());
        message = message.replaceAll("\\$\\{emprunteur:name\\}", lending.getThePerson().getName());
        message = message.replaceAll("\\$\\{emprunt:date\\}", lending.getDate().toString());
        message = message.replaceAll("\\$\\{film:title\\}", daoFilms.getFilm(lending.getTheOther()).getTitle());

        mail.setMessage(message);
        mail.setSubject(Managers.getManager(ILanguageManager.class).getMessage("lendings.mail.borrower.subject"));
        mail.setTo(new String[]{lending.getThePerson().getEmail()});

        try {
            MailUtils.send(mail, Managers.getCore().getConfiguration().getSmtpHost());
        } catch (MessagingException e) {
            Managers.getManager(IErrorManager.class).addError(new InternationalizedError("jtheque.errors.mail"));

            logger.error(e);
        }
    }
}