package com.farfocle.password_validator.rules;

import com.farfocle.password_validator.PasswordError;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SpecialCharactersRule extends CharactersRule {

    private final Set<Integer> specialCharacters;

    private SpecialCharactersRule(int value, Set<Integer> specialCharacters) {
        super(value);
        this.specialCharacters = specialCharacters;
    }

    @Override
    protected boolean checkCharacter(int character) {
        return specialCharacters.contains(character);
    }

    @Override
    public PasswordError getErrorType() {
        return PasswordError.SPECIAL_CHARACTERS;
    }

    public static class Builder extends CharactersRule.Builder<Builder, SpecialCharactersRule>{

        private final String DEFAULT_SPECIALS = "@ % + / \\\\ \\' \\# ! $ ^ : . ( ) { } [ ] ~ _ -";
        private Set<Integer> characters;

        public Builder(int value) {
            super(value);
        }

        public Builder setCharacters(List<Character> characters){
            return setCharactersFromCollection(characters);
        }

        private Builder setCharactersFromCollection(Collection<Character> collection){
            if(collection != null){
                this.characters = collection.stream().map(x->(int)x).collect(Collectors.toSet());
            }
            return this;
        }

        public Builder setCharacters(Set<Character> characters){
             return setCharactersFromCollection(characters);
        }

        @Override
        public SpecialCharactersRule build() {
            Set<Integer> characterSet = this.characters != null ? this.characters :
                    DEFAULT_SPECIALS.chars().filter(x -> !Character.isSpaceChar(x)).boxed().collect(Collectors.toSet());
            SpecialCharactersRule rule = new SpecialCharactersRule(value, characterSet);
            super.setup(rule);
            return rule;
        }
    }
}
