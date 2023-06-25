package com.wordz.domain;

public class WordSelection {
    private final WordRepository repository;
    private final NumerosAleatorios random;

    public WordSelection(WordRepository repository, NumerosAleatorios random) {
        this.repository = repository;
        this.random = random;
    }

    public String chooseRandomWord() {
        try {
            int wordNumber = random.next(repository.highestWordNumber());
            return repository.fetchWordByNumber(wordNumber);
        } catch (WordRepositoryException wre){
            throw new WordSelectionException("No se pudo seleccionar la palabra", wre);
        }
    }
}
